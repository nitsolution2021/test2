package com.dcbox.api.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConnection {

	@Value("${spring.datasource.driver-class-name}")
	private static String driver = "com.mysql.jdbc.Driver";

	@Value("${spring.datasource.url}")
	private static String url = "jdbc:mysql://decisionbox-mysql.ca0owz22hpqm.ap-south-1.rds.amazonaws.com:3306/flowable?characterEncoding=UTF-8";

	@Value("${spring.datasource.username}")
	private static String username = "datadox";

	@Value("${spring.datasource.password}")
	private static String pwd = "ruucP2kyzpSfVLc48ati";
	
	
	public static Connection dbConnection;

	@Bean
	public static Connection getConnection() {
		try {
			if (null == dbConnection) {
				Class.forName(driver);
				dbConnection = DriverManager.getConnection(url, username, pwd);
			}
		} catch (Exception e) {
			System.out.println("Exception [DBConnection] :" + e.getMessage());
			e.printStackTrace();
		}
		return dbConnection;
	}
	
	public static void main(String[] args) {
		System.out.println(getConnection());
	}

}
