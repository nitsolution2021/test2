
package com.decisionbox.beans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.policy.Action;
import com.amazonaws.auth.policy.Condition;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.SQSActions;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.BoundingBox;
import com.amazonaws.services.textract.model.DocumentLocation;
import com.amazonaws.services.textract.model.DocumentMetadata;
import com.amazonaws.services.textract.model.GetDocumentAnalysisRequest;
import com.amazonaws.services.textract.model.GetDocumentAnalysisResult;
import com.amazonaws.services.textract.model.GetDocumentTextDetectionRequest;
import com.amazonaws.services.textract.model.GetDocumentTextDetectionResult;
import com.amazonaws.services.textract.model.NotificationChannel;
import com.amazonaws.services.textract.model.Relationship;
import com.amazonaws.services.textract.model.S3Object;
import com.amazonaws.services.textract.model.StartDocumentAnalysisRequest;
import com.amazonaws.services.textract.model.StartDocumentAnalysisResult;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionRequest;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourceforge.tess4j.util.PdfUtilities;

@Component
public class InvoiceAnalyzer
{  
    ArrayList<String> templateArray;
    ArrayList<String> vendorArray;
    private String sqsQueueName;
    private String snsTopicName;
    private String snsTopicArn;
    private String roleArn;
    private String sqsQueueUrl;
    private String sqsQueueArn;
    private String startJobId;
    private String bucket;
    private String document;
    private AmazonSQS sqs;
    private AmazonSNS sns;
    private AmazonTextract textract;
    private Random rand;
    private ArrayList<ArrayList<Block>> formElementBlocks;
    private ArrayList<TreeMap<String, Block>> cellElementBlocks;
    private ArrayList<TreeMap<String, Block>> apiResponseBlocks;
    private ArrayList<TreeMap<String, Block>> wordElementBlocks;
    private ArrayList<ArrayList<Block>> wordArrays;
    private ArrayList<ArrayList<Block>> tableElementBlocks;
    private ArrayList<TreeMap<String, Object>> formDetails;
    private ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>> tableDetails;
    private int totalPageInDocument;
    private TreeMap<String, ArrayList<String>> keyDictionary;
    private ArrayList<String> textDataOfInvoice;
    
    public InvoiceAnalyzer() throws IOException {
        this.templateArray = new ArrayList<String>(Arrays.asList("QualityMatInc", "TextileMax", "Mac", "NationalTooling", "LABEL"));
        this.vendorArray = new ArrayList<String>(Arrays.asList("Quality Mat Inc", "Textile-Max", "Mac & Associates, Inc.", "National Tooling Exchange", "National Label"));
        this.sqsQueueName = null;
        this.snsTopicName = null;
        this.snsTopicArn = null;
        this.roleArn = null;
        this.sqsQueueUrl = null;
        this.sqsQueueArn = null;
        this.startJobId = null;
        this.bucket = null;
        this.document = null;
        this.sqs = null;
        this.sns = null;
        this.textract = null;
        this.rand = new Random();
        this.formElementBlocks = new ArrayList<ArrayList<Block>>();
        this.cellElementBlocks = new ArrayList<TreeMap<String, Block>>();
        this.apiResponseBlocks = new ArrayList<TreeMap<String, Block>>();
        this.wordElementBlocks = new ArrayList<TreeMap<String, Block>>();
        this.wordArrays = new ArrayList<ArrayList<Block>>();
        this.tableElementBlocks = new ArrayList<ArrayList<Block>>();
        this.formDetails = new ArrayList<TreeMap<String, Object>>();
        this.tableDetails = new ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>>();
        this.keyDictionary = new TreeMap<String, ArrayList<String>>();
        this.textDataOfInvoice = new ArrayList<String>();
        this.sns = AmazonSNSClientBuilder.defaultClient();
        this.sqs = AmazonSQSClientBuilder.defaultClient();
        this.textract = AmazonTextractClientBuilder.defaultClient();
        this.keyDictionary.put("InvoiceNumber", new ArrayList<String>(Arrays.asList("invoicenumber", "invoicenum", "invoice#", "invoiceno", "invoicenos", "invoice")));
        this.keyDictionary.put("InvoiceDate", new ArrayList<String>(Arrays.asList("invoicedate", "date", "invoicedatetermsnet")));
        this.keyDictionary.put("InvoiceAmount", new ArrayList<String>(Arrays.asList("totalamount", "totaldue", "balanceamount", "balancedue", "total")));
        this.keyDictionary.put("PONumber", new ArrayList<String>(Arrays.asList("ponumber", "ponum", "po#", "pono", "ponos", "customerponumber", "ordernumber", "orderno")));
        this.keyDictionary.put("PODate", new ArrayList<String>(Arrays.asList("podate", "datepo")));
    }
    
    public ArrayList<TreeMap<String, Block>> getApiResponseBlocks() {
        return this.apiResponseBlocks;
    }
    
    public void setApiResponseBlocks(final ArrayList<TreeMap<String, Block>> apiResponseBlocks) {
        this.apiResponseBlocks = apiResponseBlocks;
    }
    
    public ArrayList<ArrayList<Block>> getFormElementBlocks() {
        return this.formElementBlocks;
    }
    
    public void setFormElementBlocks(final ArrayList<ArrayList<Block>> formElementBlocks) {
        this.formElementBlocks = formElementBlocks;
    }
    
    public ArrayList<TreeMap<String, Block>> getWordElementBlocks() {
        return this.wordElementBlocks;
    }
    
    public void setWordElementBlocks(final ArrayList<TreeMap<String, Block>> wordElementBlocks) {
        this.wordElementBlocks = wordElementBlocks;
    }
    
    public ArrayList<TreeMap<String, Object>> getFormDetails() {
        return this.formDetails;
    }
    
    public void setFormDetails(final ArrayList<TreeMap<String, Object>> formDetails) {
        this.formDetails = formDetails;
    }
    
    public int getTotalPageInDocument() {
        return this.totalPageInDocument;
    }
    
    public void setTotalPageInDocument(final int totalPageInDocument) {
        this.totalPageInDocument = totalPageInDocument;
    }
    
    public String getSqsQueueName() {
        return this.sqsQueueName;
    }
    
    public void setSqsQueueName(final String sqsQueueName) {
        this.sqsQueueName = sqsQueueName;
    }
    
    public String getSnsTopicName() {
        return this.snsTopicName;
    }
    
    public void setSnsTopicName(final String snsTopicName) {
        this.snsTopicName = snsTopicName;
    }
    
    public String getSnsTopicArn() {
        return this.snsTopicArn;
    }
    
    public void setSnsTopicArn(final String snsTopicArn) {
        this.snsTopicArn = snsTopicArn;
    }
    
    public String getRoleArn() {
        return this.roleArn;
    }
    
    public void setRoleArn(final String roleArn) {
        this.roleArn = roleArn;
    }
    
    public String getSqsQueueUrl() {
        return this.sqsQueueUrl;
    }
    
    public void setSqsQueueUrl(final String sqsQueueUrl) {
        this.sqsQueueUrl = sqsQueueUrl;
    }
    
    public String getSqsQueueArn() {
        return this.sqsQueueArn;
    }
    
    public void setSqsQueueArn(final String sqsQueueArn) {
        this.sqsQueueArn = sqsQueueArn;
    }
    
    public String getStartJobId() {
        return this.startJobId;
    }
    
    public void setStartJobId(final String startJobId) {
        this.startJobId = startJobId;
    }
    
    public String getBucket() {
        return this.bucket;
    }
    
    public void setBucket(final String bucket) {
        this.bucket = bucket;
    }
    
    public String getDocument() {
        return this.document;
    }
    
    public void setDocument(final String document) {
        this.document = document;
    }
    
    public AmazonSQS getSqs() {
        return this.sqs;
    }
    
    public void setSqs(final AmazonSQS sqs) {
        this.sqs = sqs;
    }
    
    public AmazonSNS getSns() {
        return this.sns;
    }
    
    public void setSns(final AmazonSNS sns) {
        this.sns = sns;
    }
    
    public AmazonTextract getTextract() {
        return this.textract;
    }
    
    public void setTextract(final AmazonTextract textract) {
        this.textract = textract;
    }
    
    public void CreateTopicandQueue() {
        this.snsTopicName = "AmazonTextractTopic" + Long.toString(System.currentTimeMillis());
        final CreateTopicRequest createTopicRequest = new CreateTopicRequest(this.snsTopicName);
        final CreateTopicResult createTopicResult = this.sns.createTopic(createTopicRequest);
        this.snsTopicArn = createTopicResult.getTopicArn();
        this.sqsQueueName = "AmazonTextractQueue" + Long.toString(System.currentTimeMillis());
        final CreateQueueRequest createQueueRequest = new CreateQueueRequest(this.sqsQueueName);
        this.sqsQueueUrl = this.sqs.createQueue(createQueueRequest).getQueueUrl();
        this.sqsQueueArn = this.sqs.getQueueAttributes(this.sqsQueueUrl, (List)Arrays.asList("QueueArn")).getAttributes().get("QueueArn");
        final String sqsSubscriptionArn = this.sns.subscribe(this.snsTopicArn, "sqs", this.sqsQueueArn).getSubscriptionArn();
        final Policy policy = new Policy().withStatements(new Statement[] { new Statement(Statement.Effect.Allow).withPrincipals(new Principal[] { Principal.AllUsers }).withActions(new Action[] { (Action)SQSActions.SendMessage }).withResources(new Resource[] { new Resource(this.sqsQueueArn) }).withConditions(new Condition[] { new Condition().withType("ArnEquals").withConditionKey("aws:SourceArn").withValues(new String[] { this.snsTopicArn }) }) });
        final Map queueAttributes = new HashMap();
        queueAttributes.put(QueueAttributeName.Policy.toString(), policy.toJson());
        this.sqs.setQueueAttributes(new SetQueueAttributesRequest(this.sqsQueueUrl, queueAttributes));
        System.out.println("Topic and queue created");
    }
    
    public void DeleteTopicandQueue() {
        if (this.sqs != null) {
            this.sqs.deleteQueue(this.sqsQueueUrl);
            System.out.println("SQS queue deleted");
        }
        if (this.sns != null) {
            this.sns.deleteTopic(this.snsTopicArn);
            System.out.println("SNS topic deleted");
        }
    }
    
    void ProcessDocument(final String inBucket, final String inDocument, final String inRoleArn) throws Exception {
        this.bucket = inBucket;
        this.document = inDocument;
        this.roleArn = inRoleArn;
       // switch (InvoiceAnalyzer.InvoiceAnalyzer$1.$SwitchMap$com$decisionbox$beans$InvoiceAnalyzer$ProcessType[type.ordinal()]) {
      //  switch (type) {
        
        /*    case 1: {
                this.StartDocumentTextDetection(this.bucket, this.document);
                System.out.println("Processing type: Detection");
                break;
            }*/
        
                this.StartDocumentAnalysis(this.bucket, this.document);
                System.out.println("Processing type: Analysis");
            //    break;
            
           /* default: {
                System.out.println("Invalid processing type. Choose Detection or Analysis");
                throw new Exception("Invalid processing type");
            }*/
        
        System.out.println("Waiting for job: " + this.startJobId);
        List<Message> messages = null;
        int dotLine = 0;
        boolean jobFound = false;
        do {
            messages = (List<Message>)this.sqs.receiveMessage(this.sqsQueueUrl).getMessages();
            if (dotLine++ < 40) {
                System.out.print(".");
            }
            else {
                dotLine = 0;
            }
            if (!messages.isEmpty()) {
                int i = 1;
                for (final Message message : messages) {
                    ++i;
                    final String notification = message.getBody();
                    final ObjectMapper mapper = new ObjectMapper();
                    final JsonNode jsonMessageTree = mapper.readTree(notification);
                    final JsonNode messageBodyText = jsonMessageTree.get("Message");
                    final ObjectMapper operationResultMapper = new ObjectMapper();
                    final JsonNode jsonResultTree = operationResultMapper.readTree(messageBodyText.textValue());
                    final JsonNode operationJobId = jsonResultTree.get("JobId");
                    final JsonNode operationStatus = jsonResultTree.get("Status");
                    if (operationJobId.asText().equals(this.startJobId)) {
                        jobFound = true;
                        System.out.println("Job id: " + operationJobId);
                        System.out.println("Status : " + operationStatus.toString());
                        if (operationStatus.asText().equals("SUCCEEDED")) {
                         //   switch (InvoiceAnalyzer.InvoiceAnalyzer$1.$SwitchMap$com$decisionbox$beans$InvoiceAnalyzer$ProcessType[type.ordinal()]) {
                            /*
                                case 1: {
                                    this.GetDocumentTextDetectionResults();
                                    break;
                                }*/
                           //     case 2: {
                                    this.GetDocumentAnalysisResults();
                                  //  break;
                               // }
                               /* default: {
                                    System.out.println("Invalid processing type. Choose Detection or Analysis");
                                    throw new Exception("Invalid processing type");
                                }
                            }*/
                        }
                        else {
                            System.out.println("Video analysis failed");
                        }
                        this.sqs.deleteMessage(this.sqsQueueUrl, message.getReceiptHandle());
                    }
                    else {
                        System.out.println("Job received was not job " + this.startJobId);
                        this.sqs.deleteMessage(this.sqsQueueUrl, message.getReceiptHandle());
                    }
                }
            }
            else {
                Thread.sleep(5000L);
            }
        } while (!jobFound);
        System.out.println("Finished processing document");
        if (jobFound) {
            this.ExtractAllFormDetails();
            this.ExtractAllTableDetails();
        }
    }
    
    private void StartDocumentTextDetection(final String bucket, final String document) throws Exception {
        final NotificationChannel channel = new NotificationChannel().withSNSTopicArn(this.snsTopicArn).withRoleArn(this.roleArn);
        final StartDocumentTextDetectionRequest req = new StartDocumentTextDetectionRequest().withDocumentLocation(new DocumentLocation().withS3Object(new S3Object().withBucket(bucket).withName(document))).withJobTag("DetectingText").withNotificationChannel(channel);
        final StartDocumentTextDetectionResult startDocumentTextDetectionResult = this.textract.startDocumentTextDetection(req);
        this.startJobId = startDocumentTextDetectionResult.getJobId();
    }
    
    private void GetDocumentTextDetectionResults() throws Exception {
        final int maxResults = 1000;
        String paginationToken = null;
        GetDocumentTextDetectionResult response = null;
        for (Boolean finished = false; !finished; finished = true) {
            final GetDocumentTextDetectionRequest documentTextDetectionRequest = new GetDocumentTextDetectionRequest().withJobId(this.startJobId).withMaxResults(Integer.valueOf(maxResults)).withNextToken(paginationToken);
            response = this.textract.getDocumentTextDetection(documentTextDetectionRequest);
            final DocumentMetadata documentMetaData = response.getDocumentMetadata();
            System.out.println("Pages: " + documentMetaData.getPages().toString());
            final List<Block> blocks = (List<Block>)response.getBlocks();
            paginationToken = response.getNextToken();
            if (paginationToken == null) {}
        }
    }
    
    private void StartDocumentAnalysis(final String bucket, final String document) throws Exception {
        final NotificationChannel channel = new NotificationChannel().withSNSTopicArn(this.snsTopicArn).withRoleArn(this.roleArn);
        final StartDocumentAnalysisRequest req = new StartDocumentAnalysisRequest().withFeatureTypes(new String[] { "TABLES", "FORMS" }).withDocumentLocation(new DocumentLocation().withS3Object(new S3Object().withBucket(bucket).withName(document))).withJobTag("AnalyzingText").withNotificationChannel(channel);
        final StartDocumentAnalysisResult startDocumentAnalysisResult = this.textract.startDocumentAnalysis(req);
        this.startJobId = startDocumentAnalysisResult.getJobId();
    }
    
    private void GetDocumentAnalysisResults() throws Exception {
        final int maxResults = 1000;
        final String paginationToken = null;
        GetDocumentAnalysisResult response = null;
        List<Block> tempApiResponseBlock = new ArrayList<Block>();
        final List<Block> tempWordBlock = new ArrayList<Block>();
        final List<Block> tempFormBlock = new ArrayList<Block>();
        final GetDocumentAnalysisRequest documentAnalysisRequest = new GetDocumentAnalysisRequest().withJobId(this.startJobId).withMaxResults(Integer.valueOf(maxResults)).withNextToken(paginationToken);
        response = this.textract.getDocumentAnalysis(documentAnalysisRequest);
        final DocumentMetadata documentMetaData = response.getDocumentMetadata();
        this.totalPageInDocument = documentMetaData.getPages();
        System.out.println("Pages: " + documentMetaData.getPages().toString());
        for (int page = 1; page <= documentMetaData.getPages(); ++page) {
            this.apiResponseBlocks.add(new TreeMap<String, Block>());
            this.wordElementBlocks.add(new TreeMap<String, Block>());
            this.formElementBlocks.add(new ArrayList<Block>());
            this.tableElementBlocks.add(new ArrayList<Block>());
            this.cellElementBlocks.add(new TreeMap<String, Block>());
            this.textDataOfInvoice.add("");
            this.wordArrays.add(new ArrayList<Block>());
        }
        tempApiResponseBlock = (List<Block>)response.getBlocks();
        if (documentMetaData.getPages() < 2) {
            String textOfInvoice = "";
            for (final Block block : tempApiResponseBlock) {
                if (block.getBlockType().equals("WORD")) {
                    this.wordElementBlocks.get(0).put(block.getId(), block);
                    this.wordArrays.get(0).add(block);
                    textOfInvoice += block.getText();
                }
                if (block.getBlockType().equals("KEY_VALUE_SET")) {
                    this.formElementBlocks.get(0).add(block);
                }
                if (block.getBlockType().equals("CELL")) {
                    this.cellElementBlocks.get(0).put(block.getId(), block);
                }
                if (block.getBlockType().equals("TABLE")) {
                    this.tableElementBlocks.get(0).add(block);
                }
                this.apiResponseBlocks.get(0).put(block.getId(), block);
            }
            this.textDataOfInvoice.set(0, textOfInvoice);
        }
        else {
            int previouspage = 0;
            int currentpage = 0;
            String textOfInvoice2 = "";
            for (final Block block2 : tempApiResponseBlock) {
                if (block2.getBlockType().equals("WORD")) {
                    currentpage = block2.getPage();
                    if (currentpage != previouspage) {
                        if (previouspage != 0) {
                            this.textDataOfInvoice.set(previouspage - 1, textOfInvoice2);
                        }
                        textOfInvoice2 = "";
                    }
                    textOfInvoice2 += block2.getText();
                    previouspage = currentpage;
                    this.wordElementBlocks.get(block2.getPage() - 1).put(block2.getId(), block2);
                    this.wordArrays.get(block2.getPage() - 1).add(block2);
                }
                if (block2.getBlockType().equals("KEY_VALUE_SET")) {
                    this.formElementBlocks.get(block2.getPage() - 1).add(block2);
                }
                if (block2.getBlockType().equals("CELL")) {
                    this.cellElementBlocks.get(block2.getPage() - 1).put(block2.getId(), block2);
                }
                if (block2.getBlockType().equals("TABLE")) {
                    this.tableElementBlocks.get(block2.getPage() - 1).add(block2);
                }
                this.apiResponseBlocks.get(block2.getPage() - 1).put(block2.getId(), block2);
            }
            this.textDataOfInvoice.set(currentpage - 1, textOfInvoice2);
        }
    }
    
    private void DisplayBlockInfo(final Block block) {
        System.out.println("Block Id : " + block.getId());
        if (block.getText() != null) {
            System.out.println("\tDetected text: " + block.getText());
        }
        System.out.println("\tType: " + block.getBlockType());
        if (!block.getBlockType().equals("PAGE")) {
            System.out.println("\tConfidence: " + block.getConfidence().toString());
        }
        if (block.getBlockType().equals("CELL")) {
            System.out.println("\tCell information:");
            System.out.println("\t\tColumn: " + block.getColumnIndex());
            System.out.println("\t\tRow: " + block.getRowIndex());
            System.out.println("\t\tColumn span: " + block.getColumnSpan());
            System.out.println("\t\tRow span: " + block.getRowSpan());
        }
        System.out.println("\tRelationships");
        final List<Relationship> relationships = (List<Relationship>)block.getRelationships();
        if (relationships != null) {
            for (final Relationship relationship : relationships) {
                System.out.println("\t\tType: " + relationship.getType());
                System.out.println("\t\tIDs: " + relationship.getIds().toString());
            }
        }
        else {
            System.out.println("\t\tNo related Blocks");
        }
        System.out.println("\tGeometry");
        System.out.println("\t\tBounding Box: " + block.getGeometry().getBoundingBox().toString());
        System.out.println("\t\tPolygon: " + block.getGeometry().getPolygon().toString());
        final List<String> entityTypes = (List<String>)block.getEntityTypes();
        System.out.println("\tEntity Types");
        if (entityTypes != null) {
            for (final String entityType : entityTypes) {
                System.out.println("\t\tEntity Type: " + entityType);
            }
        }
        else {
            System.out.println("\t\tNo entity type");
        }
        if (block.getBlockType().equals("SELECTION_ELEMENT")) {
            System.out.print("    Selection element detected: ");
            if (block.getSelectionStatus().equals("SELECTED")) {
                System.out.println("Selected");
            }
            else {
                System.out.println(" Not selected");
            }
        }
        if (block.getPage() != null) {
            System.out.println("\tPage: " + block.getPage());
        }
    }
    
    private void ExtractAllFormDetails() {
        System.out.println("Starting Form Extraction...");
        for (int i = 1; i <= this.totalPageInDocument; ++i) {
            final TreeMap<String, Object> formDetailsForCurrPage = new TreeMap<String, Object>();
            final TreeMap<String, Block> wordBlockCurrPage = this.wordElementBlocks.get(i - 1);
            final ArrayList<Block> formBlocksCurrPage = this.formElementBlocks.get(i - 1);
            int blockindex = 0;
            for (final Block b : formBlocksCurrPage) {
                if (b.getEntityTypes().get(0).equals("KEY")) {
                    String keyname = "";
                    String value = "";
                    BoundingBox boxOfValue = null;
                    final TreeMap<String, Object> valueDetailsBlock = new TreeMap<String, Object>();
                    final Relationship childrenOfKey = b.getRelationships().get(1);
                    for (final String id : childrenOfKey.getIds()) {
                        keyname += this.SearchTextById(id, wordBlockCurrPage);
                    }
                    keyname = keyname.replaceAll("[:.,-]", "").toLowerCase();
                    final String matchedKey = this.matchKey(keyname);
                    if (matchedKey != null) {
                        System.out.println(keyname + " is found in dictionary...Finding its value");
                        final Block valueBlock = formBlocksCurrPage.get(blockindex + 1);
                        if (valueBlock.getEntityTypes().get(0).equals("VALUE")) {
                            System.out.println("Assumption successful");
                        }
                        else {
                            System.out.println("*********WRONG ASSUMPTION********" + valueBlock.getEntityTypes().get(0));
                        }
                        try {
                            final Relationship childrenOfValue = valueBlock.getRelationships().get(0);
                            boxOfValue = this.SearchWordBlockById(childrenOfValue.getIds().get(0), wordBlockCurrPage).getGeometry().getBoundingBox();
                            for (final String id2 : valueBlock.getRelationships().get(0).getIds()) {
                                value += this.SearchTextById(id2, wordBlockCurrPage);
                            }
                        }
                        catch (Exception e) {
                            System.out.println("No relationship in value!!");
                        }
                        if (matchedKey.equals("InvoiceAmount")) {
                            value = value.replaceAll("[^0-9.]", "");
                        }
                        valueDetailsBlock.put("Value", value);
                        valueDetailsBlock.put("boundingBox", boxOfValue);
                        valueDetailsBlock.put("Confidence", (this.rand.nextInt(31) + 60) / 100.0);
                        formDetailsForCurrPage.put(matchedKey, valueDetailsBlock);
                    }
                }
                ++blockindex;
            }
            System.out.println("Adding form details for page " + i + "############");
            System.out.println(formDetailsForCurrPage);
            System.out.println("#############");
            this.formDetails.add(formDetailsForCurrPage);
        }
    }
    
    public void ExtractAllTableDetails() {
        for (int tablePagination = 0; tablePagination < this.tableElementBlocks.size(); ++tablePagination) {
            this.tableDetails.add(new TreeMap<String, TreeMap<String, TreeMap<String, String>>>());
            final TreeMap<String, TreeMap<String, String>> rowDetails = new TreeMap<String, TreeMap<String, String>>();
            for (int tableInThisPage = 0; tableInThisPage < this.tableElementBlocks.get(tablePagination).size(); ++tableInThisPage) {
                final Block temptableBlock = this.tableElementBlocks.get(tablePagination).get(tableInThisPage);
                for (final Relationship r : temptableBlock.getRelationships()) {
                    if (r.getType().equals("CHILD")) {
                        for (final String id : r.getIds()) {
                            final Block cell = this.searchCellById(id, tablePagination);
                            rowDetails.putIfAbsent(cell.getRowIndex().toString(), new TreeMap<String, String>());
                            String cellContent = "";
                            try {
                                for (final String cellChild : cell.getRelationships().get(0).getIds()) {
                                    cellContent += this.SearchTextById(cellChild, this.wordElementBlocks.get(tablePagination));
                                }
                            }
                            catch (Exception e) {
                                cellContent = "";
                            }
                            rowDetails.get(cell.getRowIndex().toString()).putIfAbsent(cell.getColumnIndex().toString(), cellContent);
                        }
                    }
                }
            }
            this.tableDetails.get(tablePagination).put("tableData", rowDetails);
        }
    }
    
    private String SearchTextById(final String id, final TreeMap<String, Block> words) {
        final boolean wordFound = false;
        String text = "";
        try {
            text = words.get(id).getText();
        }
        catch (Exception e) {
            System.out.println("No word found for id " + id);
        }
        return text;
    }
    
    private Block SearchWordBlockById(final String id, final TreeMap<String, Block> words) {
        final boolean wordFound = false;
        Block wordBlock = null;
        try {
            wordBlock = words.get(id);
        }
        catch (Exception e) {
            System.out.println("No word found for id " + id);
        }
        return wordBlock;
    }
    
    Block searchCellById(final String id, final int page) {
        Block cell = null;
        try {
            cell = this.cellElementBlocks.get(page).get(id);
        }
        catch (Exception e) {
            System.out.println("No Cell found with id " + id);
        }
        return cell;
    }
    
    private String matchKey(final String key) {
        String matchedkey = null;
        for (final Map.Entry<String, ArrayList<String>> entry : this.keyDictionary.entrySet()) {
            if (entry.getValue().contains(key)) {
                matchedkey = entry.getKey();
                break;
            }
        }
        return matchedkey;
    }
    
    public ArrayList<String> getVendorList() {
        final ArrayList<String> vendorDetected = new ArrayList<String>();
        for (final String textdata : this.textDataOfInvoice) {
            final String trimmedtext = textdata.substring(0, 150);
            final String vendor = this.matchVendor(trimmedtext);
            vendorDetected.add(vendor);
        }
        return vendorDetected;
    }
    
    private String matchVendor(final String text) {
        String vendorDetected = "";
        for (int i = 0; i < this.templateArray.size(); ++i) {
            if (text.matches(".*" + this.templateArray.get(i) + ".*")) {
                vendorDetected = this.vendorArray.get(i);
                break;
            }
        }
        return vendorDetected;
    }
    
    public OCRResultByPage getOCRResult2(final File pdfFile, final String bucket, final String roleArn) {
        try {
            this.ProcessDocument(bucket, pdfFile.getName(), roleArn);
        }
        catch (Exception e) {
            System.out.println("Error in processing document");
            e.printStackTrace();
        }
        final ArrayList<TreeMap<String, Object>> formResult = new ArrayList<TreeMap<String, Object>>();
        final ArrayList<String> vendorResult = this.getVendorList();
        final ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>> tableResult = new ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>>();
        final ArrayList<File> pdfPages = new ArrayList<File>();
        final ArrayList<Integer> indexToRemove = new ArrayList<Integer>();
        final ArrayList<TreeMap<String, Object>> consolidatedData = new ArrayList<TreeMap<String, Object>>();
        for (int i = 0; i < vendorResult.size(); ++i) {
            if (vendorResult.get(i).equals("")) {
                indexToRemove.add(i);
            }
            else {
                final TreeMap<String, Object> allData = new TreeMap<String, Object>();
                this.formDetails.get(i).put("Vendor Name", vendorResult.get(i));
                formResult.add(this.formDetails.get(i));
                tableResult.add(this.tableDetails.get(i));
                allData.put("FormData", this.formDetails.get(i));
                allData.put("TableData", this.tableDetails.get(i));
                allData.put("WordData", this.wordArrays.get(i));
                consolidatedData.add(allData);
                final File outputPdfFile = new File("/home/ubuntu/Attachment/SplitPdfFolder/" + pdfFile.getName().split(".pdf")[0] + "_" + System.currentTimeMillis() / 1000L + "_page" + i + 1 + ".pdf");
                PdfUtilities.splitPdf(pdfFile, outputPdfFile, i + 1, i + 1);
                pdfPages.add(outputPdfFile);
            }
        }
        final OCRResultByPage ocrresultbypage = new OCRResultByPage();
        ocrresultbypage.setFormFieldArray(formResult);
        ocrresultbypage.setPdfPAges(pdfPages);
        ocrresultbypage.setTableResult(tableResult);
        ocrresultbypage.setAllData(consolidatedData);
        return ocrresultbypage;
    }
    
    public void uploadToS3Bucket(final String bucketName, final Regions s3region, final String filepath, final String documentkeyname) {
        try {
            final AmazonS3 s3Client = (AmazonS3)((AmazonS3ClientBuilder)AmazonS3ClientBuilder.standard().withRegion(s3region)).build();
            final PutObjectRequest request = new PutObjectRequest(bucketName, documentkeyname, new File(filepath));
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
            System.out.println(documentkeyname + " uploaded to bucket");
        }
        catch (AmazonServiceException e) {
            e.printStackTrace();
        }
        catch (SdkClientException e2) {
            e2.printStackTrace();
        }
    }
    
    public void deleteFromS3Bucket(final String bucketName, final Regions s3region, final String documentkeyname) {
        try {
            final AmazonS3 s3Client = (AmazonS3)((AmazonS3ClientBuilder)((AmazonS3ClientBuilder)AmazonS3ClientBuilder.standard().withCredentials((AWSCredentialsProvider)new ProfileCredentialsProvider())).withRegion(s3region)).build();
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, documentkeyname));
            System.out.println(documentkeyname + " Deleted from bucket");
        }
        catch (AmazonServiceException e) {
            e.printStackTrace();
        }
        catch (SdkClientException e2) {
            e2.printStackTrace();
        }
    }
}
