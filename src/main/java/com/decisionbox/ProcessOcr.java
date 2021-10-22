
package com.decisionbox;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.regions.Regions;
import com.decisionbox.beans.InvoiceAnalyzer;
import com.decisionbox.beans.OCRResultByPage;
import com.decisionbox.beans.SaveFormRepresentation;
import com.decisionbox.beans.VendorMaster;
import com.decisionbox.dao.LineItemDAO;
import com.decisionbox.jpa.entity.TableData;
import com.decisionbox.jpa.entity.WorkUnitEntity;
import com.decisionbox.service.AccountsPayableService;
import com.decisionbox.service.VendorMasterService;
import com.decisionbox.utils.EmailAttachmentReceiver;
import com.decisionbox.utils.InvoiceTemplate;
import com.decisionbox.utils.ProcessAttachmentPOST;
import com.decisionbox.utils.ProcessHelper;
import com.decisionbox.utils.RestTemplateHelper;
import com.decisionbox.utils.TemplateChecker;

import io.swagger.client.ApiCallback;
import io.swagger.client.api.FormsApi;
import io.swagger.client.api.HistoryTaskApi;
import io.swagger.client.api.ProcessInstancesApi;
import io.swagger.client.api.TaskAttachmentsApi;
import io.swagger.client.model.HistoricTaskInstanceQueryRequest;
import io.swagger.client.model.ProcessInstanceCreateRequest;
import io.swagger.client.model.ProcessInstanceResponse;
import io.swagger.client.model.RestVariable;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.PdfUtilities;

@Component
public class ProcessOcr
{
    Logger log;
    String bucket;
    String roleArn;
    @Autowired
    ProcessInstancesApi processInstancesApi;
    @Autowired
    HistoryTaskApi historyTaskApi;
    @Autowired
    FormsApi formsApi;
    @Autowired
    TaskAttachmentsApi taskAttachmentsApi;
    @Autowired
    VendorMasterService vendorMasterService;
    @Autowired
    LineItemDAO lineItemDao;
    @Autowired
    AccountsPayableService accountsPayableService;
    @Autowired
    EmailAttachmentReceiver emailAttachmentReceiver;
    @Autowired
    RestTemplateHelper restTemplateHelper;
    @Value("${FOLDER_ATTACHMENT_SAVE}")
    String saveDirectory;
    @Value("${FOLDER_ARCHIVE_FOLDER}")
    String archiveDirectory;
    @Value("${FOLDER_PDF_SAVE}")
    String pdfFolder;
    @Value("${FOLDER_IMAGE_SAVE}")
    String imageFolder;
    @Value("${AP_PROCESS_NAME}")
    String processName;
    @Value("${AP_PROCESS_DEFINITION_ID}")
    String processDefinitionId;
    @Value("${FLOWABLE_BASE_PATH}")
    String FLOWABLE_BASE_PATH;
    @Value("${AP_FORM_DEFINATION_ID}")
    String AP_FORM_DEFINATION_ID;
    @Value("${TESSERACT_LANG_FOLDER}")
    String TESSERACT_LANG_FOLDER;
    @Value("${GHOSTSCRIPT_LOCATION}")
    String ghostScriptLocation;
    @Value("${SPLIT_PDF_FOLDER}")
    String splitPdfFolder;
    ApiCallback<Void> callback;
    ArrayList<String> processVariables;
    String authorizationHeader;
    String authString;
    RestTemplate restTemplate;
    
    public ProcessOcr() {
        this.log = LoggerFactory.getLogger(ProcessOcr.class);
        this.bucket = "decisionboxinvoicebucket";
        this.roleArn = "arn:aws:iam::243316437650:role/DecisionBoxTextractRole";
        this.callback = null;
        this.processVariables = new ArrayList<String>(Arrays.asList("ponumber", "podate", "invoiceamount", "invoicetype", "invoicenumber", "invoicedate", "workunitid", "vendorid", "vendorname", "vendoremail", "vendoraddress", "vendoraccount", "city", "state", "country", "taskdate"));
        this.authString = "rest-admin:test";
        this.restTemplate = new RestTemplate();
    }
    
    public ArrayList<RestVariable> createArrayFromForm(final Map<String, Object> form) {
        final ArrayList<RestVariable> formarray = new ArrayList<RestVariable>();
        for (final Map.Entry<String, Object> entry : form.entrySet()) {
            formarray.add(new RestVariable((String)entry.getKey(), (String)entry.getValue()));
        }
        return formarray;
    }
    
    public String getAuthorizationHeaderForUser(final String authString) {
        final byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        final String authStringEnc = new String(authEncBytes);
        return this.authorizationHeader = "Basic " + authStringEnc;
    }
    
    public int createProcessFromNewEmail(final int processedInvoices) throws Exception {
        final ObjectMapper Obj = new ObjectMapper();
        final byte[] authEncBytes = Base64.encodeBase64(this.authString.getBytes());
        final String authStringEnc = new String(authEncBytes);
        this.authorizationHeader = "Basic " + authStringEnc;
        final int processCount = 0;
        int wuGenerator = processedInvoices;
        try {
            final File dir = new File(this.saveDirectory);
            final FileFilter fileFilterPDF = (FileFilter)new WildcardFileFilter((List)Arrays.asList("*.pdf"), IOCase.INSENSITIVE);
            final File[] fileList = dir.listFiles(fileFilterPDF);
            if (fileList.length > 0) {
                for (int i = 0; i < fileList.length; ++i) {
                    final String pdfFileName = fileList[i].getName();
                    this.log.debug("Processing PDF ----------" + pdfFileName);
                    final OCRResultByPage ocrResultByPage = this.runOCRTextract(fileList[i]);
                    final ArrayList<TreeMap<String, Object>> formfieldsArray = ocrResultByPage.getFormFieldArray();
                    final ArrayList<File> splitPdfPages = ocrResultByPage.getPdfPAges();
                    final ArrayList<TreeMap<String, TreeMap<String, TreeMap<String, String>>>> tableDataArray = ocrResultByPage.getTableResult();
                    final ArrayList<TreeMap<String, Object>> allData = ocrResultByPage.getAllData();
                    int pageCountTracker = 0;
                    for (final TreeMap<String, Object> poMap : formfieldsArray) {
                        final File fileToAttach = splitPdfPages.get(pageCountTracker);
                        final String pdfPath = fileToAttach.getPath();
                        final TreeMap<String, Object> consolidatedDataToSave = allData.get(pageCountTracker);
                        ++pageCountTracker;
                        final String jsonAllData = Obj.writeValueAsString((Object)consolidatedDataToSave);
                        Map<String, Object> formPropertyMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
                        this.log.debug(poMap.toString());
                        final ProcessInstanceCreateRequest piReq = new ProcessInstanceCreateRequest();
                        piReq.setProcessDefinitionId(this.processDefinitionId);
                        piReq.setName(this.processName + "-" + DecisionBoxUtils.currentDateString());
                        for (int k = 0; k < this.processVariables.size(); ++k) {
                            piReq.addVariablesItem(new RestVariable((String)this.processVariables.get(k)));
                        }
                        final HttpHeaders headers = new HttpHeaders();
                        headers.add("Content-Type", "application/json");
                        headers.add("Authorization", this.authorizationHeader);
                        final String startProcessInstanceURL = this.FLOWABLE_BASE_PATH + "/flowable-rest/service/runtime/process-instances";
                        this.log.debug("Process start request----->");
                        final ProcessInstanceResponse piRes = (ProcessInstanceResponse)this.restTemplateHelper.postForEntity((Class)ProcessInstanceResponse.class, startProcessInstanceURL, (Object)Obj.writeValueAsString((Object)piReq), headers);
                        final String processInstanceId = piRes.getId();
                        final List<VendorMaster> vendorList = (List<VendorMaster>)this.vendorMasterService.getVendorByName(poMap.get("Vendor Name").toString().trim());
                        VendorMaster vendor = null;
                        if (!vendorList.isEmpty()) {
                            vendor = new VendorMaster();
                            vendor = vendorList.get(0);
                        }
                        ++wuGenerator;
                        final WorkUnitEntity wue = this.accountsPayableService.createWorkUnit(piRes.getId(), wuGenerator);
                        final HistoricTaskInstanceQueryRequest htiReq = new HistoricTaskInstanceQueryRequest();
                        htiReq.setProcessInstanceId(piRes.getId());
                        final String taskIdURL = this.FLOWABLE_BASE_PATH + "/flowable-rest/service/runtime/tasks?processInstanceId=" + piRes.getId();
                        final ResponseEntity<Tasks> taskresp = (ResponseEntity<Tasks>)this.restTemplate.exchange(taskIdURL, HttpMethod.GET, new HttpEntity((MultiValueMap)headers), (Class)Tasks.class, new Object[0]);
                        final String taskId = ((Tasks)taskresp.getBody()).getData().get(0).getId();
                        this.log.debug("taskId:" + taskId);
                        final String claimTaskURL = this.FLOWABLE_BASE_PATH + "/flowable-rest/service/runtime/tasks/" + taskId;
                        final Map<String, String> claimTaskBodyMap = new TreeMap<String, String>();
                        claimTaskBodyMap.put("action", "claim");
                        claimTaskBodyMap.put("assignee", "admin");
                        final String claimTaskBody = Obj.writeValueAsString((Object)claimTaskBodyMap);
                        final String claimTaskResponse = (String)this.restTemplateHelper.postForEntity((Class)String.class, claimTaskURL, (Object)claimTaskBody, headers);
                        final String contentCreateRestCallurl = this.FLOWABLE_BASE_PATH + "/DB-task/app/rest/process-instances/" + processInstanceId + "/raw-content";
                        final String contentId = ProcessAttachmentPOST.postMultiPartRequest(contentCreateRestCallurl, fileToAttach, wue.getWorkunitId().toString() + "-" + pdfFileName, taskId, processInstanceId, Obj, pdfPath, this.authorizationHeader);
                        this.log.debug("Work Unit ID:" + wue.getWorkunitId());
                        formPropertyMap = (Map<String, Object>)ProcessHelper.fillFormFields((Map)poMap, vendor, wue.getWorkunitId(), fileToAttach, contentId);
                        final SaveFormRepresentation formRepresentation = new SaveFormRepresentation();
                        formRepresentation.setTaskId(taskId);
                        formRepresentation.setVariables((Map)formPropertyMap);
                        formRepresentation.setFormDefinitionId(this.AP_FORM_DEFINATION_ID);
                        formRepresentation.setProcessInstanceId(processInstanceId);
                        formRepresentation.setProcessDefinitionId(this.processDefinitionId);
                        final String formSubmitURL = this.FLOWABLE_BASE_PATH + "/flowable-rest/form-api/form/form-instances";
                        final String formSubmitResp = (String)this.restTemplateHelper.postForEntity((Class)String.class, formSubmitURL, (Object)Obj.writeValueAsString((Object)formRepresentation), headers);
                        final String variableUpdateURL = this.FLOWABLE_BASE_PATH + "/flowable-rest/service/runtime/process-instances/" + processInstanceId + "/variables";
                        final String variableBody = Obj.writeValueAsString((Object)this.createArrayFromForm(formPropertyMap));
                        final ResponseEntity<String> variableUpdateResponse = (ResponseEntity<String>)this.restTemplate.exchange(variableUpdateURL, HttpMethod.PUT, new HttpEntity((Object)variableBody, (MultiValueMap)headers), (Class)String.class, new Object[0]);
                        this.lineItemDao.saveLineItem(new TableData(processInstanceId, new Date(), wue.getWorkunitId(), jsonAllData));
                        System.out.println(this.lineItemDao.fetchLineItemByProcId(processInstanceId).getWorkunitid());
                        final TaskSubmissionRequestBody taskSubmission = new TaskSubmissionRequestBody("complete", new ArrayList());
                        final String taskCompleteURL = this.FLOWABLE_BASE_PATH + "/flowable-rest/service/runtime/tasks/" + taskId;
                        final String taskSubmitResp = (String)this.restTemplateHelper.postForEntity((Class)String.class, taskCompleteURL, (Object)Obj.writeValueAsString((Object)taskSubmission), headers);
                        this.log.debug("Task Submit Response -->" + taskSubmitResp);
                        this.log.debug("#################################################");
                    }
                    Files.move(FileSystems.getDefault().getPath(this.saveDirectory + File.separator + fileList[i].getName(), new String[0]), FileSystems.getDefault().getPath(this.archiveDirectory + File.separator + fileList[i].getName(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return wuGenerator;
    }
    
    public OCRResultByPage runOCR(final File pdfFile) {
        final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
        final Date date = new Date();
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        System.load(this.ghostScriptLocation);
        final OCRResultByPage ocrResultByPage = new OCRResultByPage();
        try {
            final File[] imageFile = PdfUtilities.convertPdf2Png(pdfFile);
            String result = null;
            final ITesseract instance = (ITesseract)new Tesseract();
            instance.setDatapath(this.TESSERACT_LANG_FOLDER);
            int i = 0;
            final Set<String> invoiceNos = new HashSet<String>();
            final ArrayList<TreeMap<String, String>> finalFormArray = new ArrayList<TreeMap<String, String>>();
            for (final File img : imageFile) {
                ++i;
                this.log.debug("Showing result for page " + i + "....");
                final BufferedImage bi = ImageIO.read(img);
                final File outputfile = new File(this.imageFolder + "\\" + pdfFile.getName().split(".pdf")[0] + "_image_page" + i + ".png");
                ImageIO.write(bi, "png", outputfile);
                this.log.debug("Image Dimension " + bi.getWidth() + "x" + bi.getHeight());
                final BufferedImage bigray = ImageHelper.convertImageToGrayscale(bi);
                result = instance.doOCR(bigray).replace("\n", " ").replace("\r", " ");
                final TemplateChecker tempcheck = new TemplateChecker(result);
                tempcheck.calculateTemplateFromOCRContent();
                final String templateName = tempcheck.getTemplate();
                if (templateName == null) {
                    this.log.debug("Image of Unknown Template. Skipping..");
                }
                else {
                    this.log.debug("Identified Template is " + templateName);
                    final InvoiceTemplate invTemp = new InvoiceTemplate(templateName);
                    final TreeMap<String, Rectangle> rectMaps = (TreeMap<String, Rectangle>)invTemp.getFieldRectangleMap();
                    final TreeMap<String, String> fieldMap = new TreeMap<String, String>();
                    fieldMap.put("Vendor Name", invTemp.getVendorName());
                    for (final Map.Entry<String, Rectangle> entry : rectMaps.entrySet()) {
                        String text = "";
                        if (entry.getValue() != null) {
                            text = instance.doOCR(bigray, (Rectangle)entry.getValue());
                            if (entry.getKey() == "Invoice Amount") {
                                text = text.replace(" ", "").replace(",", "").replaceAll("[$]", "").replaceAll("[^.0-9]", "");
                            }
                        }
                        fieldMap.put(entry.getKey(), text.trim());
                        System.out.println("Added " + entry.getKey() + "--->" + text + " from rectangle " + entry.getValue());
                    }
                    final boolean isInvoiceNew = invoiceNos.add(fieldMap.get("Invoice Number"));
                    if (!isInvoiceNew) {
                        this.log.debug("Duplicate Invoice. Skipping..");
                    }
                    else {
                        final File outputPdfFile = new File("/home/ubuntu/Attachment/SplitPdfFolder/" + pdfFile.getName().split(".pdf")[0] + "_" + System.currentTimeMillis() / 1000L + "_page" + i + ".pdf");
                        PdfUtilities.splitPdf(pdfFile, outputPdfFile, i, i);
                        this.log.debug("Form is " + fieldMap);
                        finalFormArray.add(fieldMap);
                        ocrResultByPage.pushToPdfPages(outputPdfFile);
                    }
                }
            }
            this.log.debug("FinalForm array is " + finalFormArray.toString());
            this.log.debug("Total pdf are " + ocrResultByPage.getPdfPAges().size());
            this.log.debug("Total forms are " + ocrResultByPage.getFormFieldArray().size());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (TesseractException e2) {
            e2.printStackTrace();
        }
        return ocrResultByPage;
    }
    
    public OCRResultByPage runOCRTextract(final File pdfFile) {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        try {
            final InvoiceAnalyzer invanalyzer = new InvoiceAnalyzer();
            invanalyzer.uploadToS3Bucket(this.bucket, Regions.AP_SOUTH_1, pdfFile.getAbsolutePath(), pdfFile.getName());
            invanalyzer.CreateTopicandQueue();
            final OCRResultByPage processedresult = invanalyzer.getOCRResult2(pdfFile, this.bucket, this.roleArn);
            invanalyzer.DeleteTopicandQueue();
            invanalyzer.deleteFromS3Bucket(this.bucket, Regions.AP_SOUTH_1, pdfFile.getName());
            return processedresult;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}