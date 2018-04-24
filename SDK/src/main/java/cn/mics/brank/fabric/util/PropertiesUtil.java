package cn.mics.brank.fabric.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	
	private static Properties properties = null;
	private static volatile PropertiesUtil _instance;
	
	private static String serviceConfigPath = "/service.properties";
	
//	private static String App_Prefix = "APP_";
	private static String Sdk_Prefix = "SDK_";
//	private static String Interface_Prefix = "INTERFACE_";
	private static String Err_Prefix = "ERR_";
//	private static String Org_Prefix = "ORG_";
//	private static String User_Prefix = "USER_";

	///////////////////////////////////////////////
	///////////////APP SERVICE SETTING/////////////
	///////////////////////////////////////////////
//	public static String CustodyIp = "CUSTODY_IP";
//	public static String CustodyPort = "CUSTODY_PORT";
//	public static String CustodyConnectTimeout = "CUSTODY_CONNECT_TIMEOUT";
//	public static String CustodyReadTimeout = "CUSTODY_READ_TIMEOUT";
//	
//	public static String ResetPW = "RESET_PW";
//	public static String MaxLoginTime = "MAX_LOGIN_TIME";
//	public static String SessionTimeout = "SESSION_TIMEOUT";
//	public static String ThreadNumber = "THREAD_NUMBER";
//	public static String OrgMappingEnable = "ORG_MAPPING_ENABLE";
//	public static String LongTimeOutUsers = "LONG_TIMEOUT_USERS";
//	
//	public static String SendMessage = "SEND_MESSAGE";
//	public static String ModifyProduct = "MODIFY_PRODUCT";
//	
//	public static String OrgFlag = "ORG_FLAG";
//	public static String FinanceServicePath = "FINANCE_SERVICE_PATH";
//	public static String AppResourcesPath = "RESOURCES_PATH";
//	public static String ProjectVersion = "PROJECT_VERSION";
//	
//	public static String EncodePW = "ENCODE_PW";
//	public static String Mock = "MOCK";
	
	///////////////////////////////////////////////
	///////////////SDK SERVICE SETTING/////////////
	///////////////////////////////////////////////
	public static String BasePath = "BASE_PATH";
	
	public static String NetworkConfigFile = "NETWORK_CONFIG_FILE";
	public static String SDKConfigFile = "SDK_CONFIG_FILE";
	public static String ParameterConfigFile = "PARAMETER_CONFIG_FILE";
	public static String ArtifactsPath = "ARTIFACTS_PATH";
	public static String GoPath = "GO_PATH";
	public static String PolicyPath = "POLICY_PATH";
	
	public static String KeyValueStoreFile = "KEY_VALUE_STORE_FILE";
	public static String SerializedNetworkFile = "SERIALIZED_NETWORK_FILE";
	
//	///////////////////////////////////////////////
//	///////////////INTERFACE SERVICE SETTING///////
//	///////////////////////////////////////////////
//	public static String ChannelManagementAppAddress = "CHANNEL_MANAGEMENT_APP_ADDRESS";
//	public static String ChannelManagementAppAddressBackup1 = "CHANNEL_MANAGEMENT_APP_ADDRESS_BACKUP1";
//	public static String ChannelManagementAppAddressBackup2 = "CHANNEL_MANAGEMENT_APP_ADDRESS_BACKUP2";
//	public static String ChannelManagementTimeOut = "CHANNEL_MANAGEMENT_TIME_OUT";
//	public static String MessageManagementServerIp = "MESSAGE_MANAGEMENT_SERVER_IP";
//	public static String MessageManagementServerPort = "MESSAGE_MANAGEMENT_SERVER_PORT";
//	public static String MessageManagementLocalIP = "MESSAGE_MANAGEMENT_LOCAL_IP";
//	public static String MessageManagementTimeOut = "MESSAGE_MANAGEMENT_TIME_OUT";
//	public static String ContentManagementLocation = "CONTENT_MANAGEMENT_LOCATION";
//	public static String ContentManagementAreaCode = "CONTENT_MANAGEMENT_AREACODE";
//	
	///////////////////////////////////////////////
	///////////////ERR MAPPING/////////////////////
	///////////////////////////////////////////////
	public static String IOExceptionId = "IO_EXCEPTION_ID";
	public static String IOExceptionMsg = "IO_EXCEPTION_MSG";
	public static String DBExceptionId = "DB_EXCEPTION_ID";
	public static String DBExceptionMsg = "DB_EXCEPTION_MSG";
	public static String DBInsertFailId = "DB_INSERT_FAIL_ID";
	public static String DBInsertFailMsg = "DB_INSERT_FAIL_MSG";
	public static String DBUpdateFailId = "DB_UPDATE_FAIL_ID";
	public static String DBUpdateFailMsg = "DB_UPDATE_FAIL_MSG";
	public static String DBDeleteFailId = "DB_DELETE_FAIL_ID";
	public static String DBDeleteFailMsg = "DB_DELETE_FAIL_MSG";
	public static String DBDataNotExistId = "DB_DATA_NOT_EXIST_ID";
	public static String DBDataNotExistMsg = "DB_DATA_NOT_EXIST_MSG";
	
	public static String OtherExceptionId = "OTHER_EXCEPTION_ID";
	public static String OtherExceptionMsg = "OTHER_EXCEPTION_MSG";
	public static String ArgMissMappingId = "ARG_MISS_MAPPING_ID";
	public static String ArgMissMappingMsg = "ARG_MISS_MAPPING_MSG";
	public static String ArgMissInvalidId = "ARG_MISS_INVALID_ID";
	public static String ArgMissInvalidMsg = "ARG_MISS_INVALID_MSG";
	public static String ExternalPlatformId = "EXTERNAL_PLATFORM_ID";
	public static String ExternalPlatformMsg = "EXTERNAL_PLATFORM_MSG";
	
	public static String OrgNotExistId = "ORG_NOT_EXIST_ID";
	public static String OrgNotExistMsg = "ORG_NOT_EXIST_MSG";
	public static String ChannelExistId = "CHANNEL_EXIST_ID";
	public static String ChannelExistMsg = "CHANNEL_EXIST_MSG";
	public static String ChannelNotExistId = "CHANNEL_NOT_EXIST_ID";
	public static String ChannelNotExistMsg = "CHANNEL_NOT_EXIST_MSG";
	public static String PeerJoinedId = "PEER_JOINED_ID";
	public static String PeerJoinedMsg = "PEER_JOINED_MSG";
	public static String PeerNotExistId = "PEER_NOT_EXIST_ID";
	public static String PeerNotExistMsg = "PEER_NOT_EXIST_MSG";
	public static String ChaincodeNotExistId = "CHAINCODE_NOT_EXIST_ID";
	public static String ChaincodeNotExistMsg = "CHAINCODE_NOT_EXIST_MSG";
	public static String ChaincodeInstallFailedId = "CHAINCODE_INSTALL_FAILED_ID";
	public static String ChaincodeInstallFailedMsg = "CHAINCODE_INSTALL_FAILED_MSG";
	public static String ProposalInconsistentId = "PROPOSAL_INCONSISTANT_ID";
	public static String ProposalInconsistentMsg = "PROPOSAL_INCONSISTANT_MSG";
	public static String EndorserNotEnoughId = "ENDORSER_NOT_ENOUGH_ID";
	public static String EndorserNotEnoughMsg = "ENDORSER_NOT_ENOUGH_MSG";
	public static String TransEventInvalidId = "TRANS_EVENT_INVALID_ID";
	public static String TransEventInvalidMsg = "TRANS_EVENT_INVALID_MSG";
	public static String TransEventFailedId = "TRANS_EVENT_FAILED_ID";
	public static String TransEventFailedMsg = "TRANS_EVENT_FAILED_MSG";
	public static String ProposalFailedId = "PROPOSAL_FAILED_ID";
	public static String ProposalFailedMsg = "PROPOSAL_FAILED_MSG";
	
	public static String UserNotLoginId = "USER_NOT_LOGIN_ID";
	public static String UserNotLoginMsg = "USER_NOT_LOGIN_MSG";
	public static String UserNotRegisterId = "USER_NOT_REGISTER_ID";
	public static String UserNotRegisterMsg = "USER_NOT_REGISTER_MSG";
	public static String UserExistId = "USER_EXIST_ID";
	public static String UserExistMsg = "USER_EXIST_MSG";
	public static String OrgExistId = "ORG_EXIST_ID";
	public static String OrgExistMsg = "ORG_EXIST_MSG";
	public static String UserNotExistId = "USER_NOT_EXIST_ID";
	public static String UserNotExistMsg = "USER_NOT_EXIST_MSG";
	public static String UserLockId = "USER_LOCK_ID";
	public static String UserLockMsg = "USER_LOCK_MSG";
	public static String UserMaxLoginId = "USER_MAX_LOGIN_ID";
	public static String UserMaxLoginMsg = "USER_MAX_LOGIN_MSG";
	public static String UserWrongPWId = "USER_WRONG_PW_ID";
	public static String UserWrongPWMsg = "USER_WRONG_PW_MSG";
	public static String UserWrongOldPWId = "USER_WRONG_OLDPW_ID";
	public static String UserWrongOldPWMsg = "USER_WRONG_OLDPW_MSG";
	public static String UserWrongOrgId = "USER_WRONG_ORG_ID";
	public static String UserWrongOrgMsg = "USER_WRONG_ORG_MSG";
	public static String UserWrongRoleId = "USER_WRONG_ROLE_ID";
	public static String UserWrongRoleMsg = "USER_WRONG_ROLE_MSG";
	public static String UserNotMatchId = "USER_NOT_MATCH_ID";
	public static String UserNotMatchMsg = "USER_NOT_MATCH_MSG";
	public static String UserListId = "USER_LIST_ID";
	public static String UserListMsg = "USER_LIST_MSG";
	
	public static String FileUploadExceptionId = "FILE_UPLOAD_EXCEPTION_ID";
	public static String FileUploadExceptionMsg = "FILE_UPLOAD_EXCEPTION_MSG";
	public static String FileUploadFailId = "FILE_UPLOAD_FAIL_ID";
	public static String FileUploadFailMsg = "FILE_UPLOAD_FAIL_MSG";
	public static String FileDownloadId = "FILE_DOWNLOAD_ID";
	public static String FileDownloadMsg = "FILE_DOWNLOAD_MSG";
	public static String FileNotExistId = "FILE_NOT_EXIST_ID";
	public static String FileNotExistMsg = "FILE_NOT_EXIST_MSG";
	public static String FileHashCodeId = "FILE_HASHCODE_ID";
	public static String FileHashCodeMsg = "FILE_HASHCODE_MSG";
	
	public static String LogListId = "LOG_LIST_ID";
	public static String LogListMsg = "LOG_LIST_MSG";
	
//	///////////////////////////////////////////////
//	///////////////ORG MAPPING/////////////////////
//	///////////////////////////////////////////////
//	private static String Default_Org_Name = "org1";
//	
//	///////////////////////////////////////////////
//	///////////////ORG-USER MAPPING////////////////
//	///////////////////////////////////////////////
////	private static String Default_User_Name = "admin";
	
	private PropertiesUtil() {
		if (properties == null) {
			properties = new Properties();
			loadConf();
		}
	}
	
	public void loadConf() {
		try {
			InputStream fileInputStream = null;
			
			fileInputStream = this.getClass().getResourceAsStream(serviceConfigPath);
			properties.load(fileInputStream);
			
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			logger.error(String.format("Service Config File: %s", e.getMessage().toString()));
		} catch (IOException e) {
			logger.error(String.format("Service Config File: %s", e.getMessage().toString()));
		}
	}

	public static PropertiesUtil getInstance() {
		if(_instance == null) {
    		synchronized (PropertiesUtil.class) {
				if (_instance == null) {
					_instance = new PropertiesUtil();
				}
			}
    	}
		return _instance;
	}

	private static String getProperty(String key) {
		try {
			if(properties.getProperty(key) == null)
				return "";
			
			String value = new String(properties.getProperty(key).getBytes(
					"ISO-8859-1"), "UTF-8");// "UTF-8"
			if (value == null || value.trim().equals(""))
				return "";
			else
				return value.trim();
		} catch (Exception e) {
			logger.error(String.format("Error: %s", e.getMessage().toString()));
			return "";
		}
	}
	
	private static String getProperty(String key, String[] args) {
		String prop = getProperty(key);
		return MessageFormat.format(prop, args);
	}
	
	//APP
//    public String getAppPropertyStr(String propName) {
//		return getProperty(App_Prefix + propName);
//	}
//    
//    public int getAppPropertyInt(String propName) {
//        return Integer.parseInt(getProperty(App_Prefix + propName));
//    }
//    
//    public boolean getAppPropertyBoolean(String propName) {
//        return getProperty(App_Prefix + propName).equals("true") ? true : false;
//    }
//    
//    public List<String> getAppSpecialUsers() {
//    	List<String> userList = new ArrayList<String>();
//    	String[] userArray = getProperty(App_Prefix + LongTimeOutUsers).split(",");
//    	for(String user : userArray)
//    		userList.add(user);
//        return userList;
//    }
    
	//SDK
	public String getNetworkConfigFile() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + NetworkConfigFile);
	}

	public String getSDKConfigFile() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + SDKConfigFile);
	}

	public String getParameterConfigFile() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + ParameterConfigFile);
	}

	public String getArtifactsPath() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + ArtifactsPath);
	}

	public String getGoPath() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + GoPath);
	}

	public String getPolicyPath() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + PolicyPath);
	}
	
	public static String getKeyValueStoreFile() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + KeyValueStoreFile);
	}

	public static String getSerializedNetworkFile() {
		return getProperty(Sdk_Prefix + BasePath) + getProperty(Sdk_Prefix + SerializedNetworkFile);
	}
//	//INTERFACE
//    public String getChannelManagementAppAddress() {
//		return getProperty(Interface_Prefix + ChannelManagementAppAddress);
//	}
//	public String getChannelManagementAppAddressBackup1() {
//		return getProperty(Interface_Prefix + ChannelManagementAppAddressBackup1);
//	}
//	public String getChannelManagementAppAddressBackup2() {
//		return getProperty(Interface_Prefix + ChannelManagementAppAddressBackup2);
//	}
//	public String getChannelManagementTimeOut() {
//		return getProperty(Interface_Prefix + ChannelManagementTimeOut);
//	}
//
//	public String getMessageManagementServerIp() {
//		return getProperty(Interface_Prefix + MessageManagementServerIp);
//	}
//	public String getMessageManagementServerPort() {
//		return getProperty(Interface_Prefix + MessageManagementServerPort);
//	}
//	public String getMessageManagementLocalIP() {
//		return getProperty(Interface_Prefix + MessageManagementLocalIP);
//	}
//	public String getMessageManagementTimeOut() {
//		return getProperty(Interface_Prefix + MessageManagementTimeOut);
//	}
//	
//	public String getContentManagementLocation() {
//		return getProperty(Interface_Prefix + ContentManagementLocation);
//	}
//	public String getContentManagementAreaCode() {
//		return getProperty(Interface_Prefix + ContentManagementAreaCode);
//	}
	//ERR
    public String getErrId(String errName) {
		return getProperty(Err_Prefix + errName);
	}
    
    public String getErrMsg(String errName) {
		return getProperty(Err_Prefix + errName);
	}
    
    public String getErrMsg(String errName, String[] args) {
		return getProperty(Err_Prefix + errName, args);
	}
//    
//    //ORG
//    //ORG-USER
//    public String getOrgName(String orgId) {
//    	String orgName = getProperty(Org_Prefix + orgId);
//    	
//    	if(orgName.equals(""))
//    		orgName = Default_Org_Name;
//    	
//    	return orgName;
//    }
//    
////    public static String getUserName(String userId) {
//////        return getProperty(userId);
////    	return ConfigUtil.getConfig().getUserNames()[0];
////    }
//    
//    public String getUserNameByOrgId(String orgId) {
//	  	return getUserNameByOrgName(getOrgName(orgId));
//	}
//    
//    public String getUserNameByOrgName(String orgName) {
//    	return getProperty(User_Prefix + orgName);
//	}
    
}
