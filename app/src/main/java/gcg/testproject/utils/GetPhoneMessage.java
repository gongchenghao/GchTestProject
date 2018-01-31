package gcg.testproject.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 作者: CyrusW.
 * 日期: 2017/10/25.
		用法：
		GetPhoneMessage.getInstance(this).setCTelephoneInfo();
        String imei1 = GetPhoneMessage.getInstance(this).getImeiSIM1();
        String imei2 = GetPhoneMessage.getInstance(this).getImeiSIM2();
 */
		

public class GetPhoneMessage {
    private TelephonyManager tm;

    private static final String TAG = GetPhoneMessage.class.getSimpleName();
    private String imeiSIM1;// IMEI号1
    private String imeiSIM2;//IMEI号2
    private String iNumeric1;//卡1手机号
    private String iNumeric2;//卡2手机号
    private boolean isSIM1Ready;//sim1 卡1是否准备好
    private boolean isSIM2Ready;//sim2 卡2是否准备好
    private String iDataConnected1 = "0";//sim1 0 no, 1 connecting, 2 connected, 3 suspended. 卡1是否连接上
    private String iDataConnected2 = "0";//sim2 卡2是否连接上
    private static GetPhoneMessage CTelephoneInfo;
    private static Context mContext;

    private GetPhoneMessage() {
    }

    public synchronized static GetPhoneMessage getInstance(Context context){
        if(CTelephoneInfo == null) {
            CTelephoneInfo = new GetPhoneMessage();
        }
        mContext = context;
        return CTelephoneInfo;
    }

    public String getImeiSIM1() {
        return imeiSIM1;
    }

    public String getImeiSIM2() {
        return imeiSIM2;
    }
    public boolean isSIM1Ready() {
        return isSIM1Ready;
    }

    public boolean isSIM2Ready() {
        return isSIM2Ready;
    }

    public boolean isDualSim(){
        return imeiSIM2 != null;
    }

    public boolean isDataConnected1(){
        if(TextUtils.equals(iDataConnected1, "2")||TextUtils.equals(iDataConnected1, "1"))
            return true;
        else
            return false;
    }

    public boolean isDataConnected2(){
        if(TextUtils.equals(iDataConnected2, "2")||TextUtils.equals(iDataConnected2, "1"))
            return true;
        else
            return false;
    }

    public String getINumeric1(){
        return iNumeric1;
    }

    public String getINumeric2(){
        return iNumeric2;
    }

    public String getINumeric(){
        if(imeiSIM2 != null){
            if(iNumeric1 != null && iNumeric1.length() > 1)
                return iNumeric1;

            if(iNumeric2 != null && iNumeric2.length() > 1)
                return iNumeric2;
        }
        return iNumeric1;
    }

    public void setCTelephoneInfo(){
        TelephonyManager telephonyManager = ((TelephonyManager)
                mContext.getSystemService(Context.TELEPHONY_SERVICE));
        CTelephoneInfo.imeiSIM1 = telephonyManager.getDeviceId();
        CTelephoneInfo.imeiSIM2 = null;
        try {
            CTelephoneInfo.imeiSIM1 = getOperatorBySlot(mContext, "getDeviceIdGemini", 0);
            CTelephoneInfo.imeiSIM2 = getOperatorBySlot(mContext, "getDeviceIdGemini", 1);
            CTelephoneInfo.iNumeric1 = getOperatorBySlot(mContext, "getSimOperatorGemini", 0);
            CTelephoneInfo.iNumeric2 = getOperatorBySlot(mContext, "getSimOperatorGemini", 1);
            CTelephoneInfo.iDataConnected1 = getOperatorBySlot(mContext, "getDataStateGemini", 0);
            CTelephoneInfo.iDataConnected2 = getOperatorBySlot(mContext, "getDataStateGemini", 1);
        } catch (GeminiMethodNotFoundException e) {
            e.printStackTrace();
            try {
                CTelephoneInfo.imeiSIM1 = getOperatorBySlot(mContext, "getDeviceId", 0);
                CTelephoneInfo.imeiSIM2 = getOperatorBySlot(mContext, "getDeviceId", 1);
                CTelephoneInfo.iNumeric1 = getOperatorBySlot(mContext, "getSimOperator", 0);
                CTelephoneInfo.iNumeric2 = getOperatorBySlot(mContext, "getSimOperator", 1);
                CTelephoneInfo.iDataConnected1 = getOperatorBySlot(mContext, "getDataState", 0);
                CTelephoneInfo.iDataConnected2 = getOperatorBySlot(mContext, "getDataState", 1);
            } catch (GeminiMethodNotFoundException e1) {
                //Call here for next manufacturer's predicted method name if you wish
                e1.printStackTrace();
            }
        }
        CTelephoneInfo.isSIM1Ready = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
        CTelephoneInfo.isSIM2Ready = false;

        try {
            CTelephoneInfo.isSIM1Ready = getSIMStateBySlot(mContext, "getSimStateGemini", 0);
            CTelephoneInfo.isSIM2Ready = getSIMStateBySlot(mContext, "getSimStateGemini", 1);
        } catch (GeminiMethodNotFoundException e) {
            e.printStackTrace();
            try {
                CTelephoneInfo.isSIM1Ready = getSIMStateBySlot(mContext, "getSimState", 0);
                CTelephoneInfo.isSIM2Ready = getSIMStateBySlot(mContext, "getSimState", 1);
            } catch (GeminiMethodNotFoundException e1) {
                //Call here for next manufacturer's predicted method name if you wish
                e1.printStackTrace();
            }
        }
    }

    private static  String getOperatorBySlot(Context context, String predictedMethodName, int slotID)
            throws GeminiMethodNotFoundException {
        String inumeric = null;
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try{
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);
            if(ob_phone != null){
                inumeric = ob_phone.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }
        return inumeric;
    }

    private static  boolean getSIMStateBySlot(Context context, String predictedMethodName, int slotID) throws GeminiMethodNotFoundException {

        boolean isReady = false;

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try{

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimStateGemini = telephonyClass.getMethod(predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimStateGemini.invoke(telephony, obParameter);

            if(ob_phone != null){
                int simState = Integer.parseInt(ob_phone.toString());
                if(simState == TelephonyManager.SIM_STATE_READY){
                    isReady = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }

        return isReady;
    }

    private static class GeminiMethodNotFoundException extends Exception {

        /**
         *
         */
        private static final long serialVersionUID = -3241033488141442594L;

        public GeminiMethodNotFoundException(String info) {
            super(info);
        }
    }

//    =============================================================================================================================================
    //获取手机的品牌
    public static String getPhonePinPai()
    {
        return android.os.Build.BRAND;
    }
    //获取手机型号
    public static String getPhoneXingHao()
    {
        return Build.MODEL;
    }


    /*
    * 电话方位：
    *
    */
    private void getTelephoneLoaction() {
        tm.getCellLocation();//CellLocation
    }

    /*
   * 电话状态：
   * 1.tm.CALL_STATE_IDLE=0          无活动
   * 2.tm.CALL_STATE_RINGING=1  响铃
   * 3.tm.CALL_STATE_OFFHOOK=2  摘机
   */
    public int getCallState() {
        return  tm.getCallState();//int
    }



    /*
     * 唯一的设备ID：
     * GSM手机的 IMEI 和 CDMA手机的 MEID.
     * Return null if device ID is not available.
     */
    public String getPhoneID() {
        return   tm.getDeviceId();//String
    }


    /*
     * 设备的软件版本号：
     * 例如：the IMEI/SV(software version) for GSM phones.
     * Return null if the software version is not available.
     */
    public String getRuanJianBanBen() {
        return   tm.getDeviceSoftwareVersion();//String
    }

    /*
     * 附近的电话的信息:
     * 类型：List<NeighboringCellInfo>
     * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
     */
    public List<NeighboringCellInfo> getPhoneInfo()
    {
        return  tm.getNeighboringCellInfo();//List<NeighboringCellInfo>
    }


    /*
     * 获取ISO标准的国家码，即国际长途区号。
     * 注意：仅当用户已在网络注册后有效。
     *       在CDMA网络中结果也许不可靠。
     */
    public String getCode()
    {
        return tm.getNetworkCountryIso();//String
    }


    /*
     * MCC+MNC(mobile country code + mobile network code)
     * 注意：仅当用户已在网络注册时有效。
     *    在CDMA网络中结果也许不可靠。
     */
    public String getNet()
    {
        return tm.getNetworkOperator();//String
    }

    /*
     * 按照字母次序的current registered operator(当前已注册的用户)的名字
     * 注意：仅当用户已在网络注册时有效。
     *    在CDMA网络中结果也许不可靠。
     */
    public String getName()
    {
        return tm.getNetworkOperatorName();//String
    }


    /*
     * 当前使用的网络类型：
     * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
       NETWORK_TYPE_GPRS     GPRS网络  1
       NETWORK_TYPE_EDGE     EDGE网络  2
       NETWORK_TYPE_UMTS     UMTS网络  3
       NETWORK_TYPE_HSDPA    HSDPA网络  8
       NETWORK_TYPE_HSUPA    HSUPA网络  9
       NETWORK_TYPE_HSPA     HSPA网络  10
       NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
       NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
       NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
       NETWORK_TYPE_1xRTT    1xRTT网络  7
     */
    public int getNetType()
    {
        return tm.getNetworkType();//int
    }

    /*
     * 手机信号类型：
     * 例如： PHONE_TYPE_NONE  无信号
       PHONE_TYPE_GSM   GSM信号
       PHONE_TYPE_CDMA  CDMA信号
     */
    public int getPhoneType()
    {
        return  tm.getPhoneType();//int
    }

    /*
     * Returns the ISO country code equivalent for the SIM provider's country code.
     * 获取ISO国家码，相当于提供SIM卡的国家码。
     *
     */
    public String getCountryCode()
    {
        return   tm.getSimCountryIso();//String
    }

  /*
   * Returns the MCC+MNC (mobile country code + mobile network code) of the provider of the SIM. 5 or 6 decimal digits.
   * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
   * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
   */
//    tm.getSimOperator();//String

  /*
   * 服务商名称：
   * 例如：中国移动、联通
   * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
   */
//    tm.getSimOperatorName();//String

  /*
   * SIM卡的序列号：
   * 需要权限：READ_PHONE_STATE
   */
//    tm.getSimSerialNumber();//String

  /*
   * SIM的状态信息：
   *  SIM_STATE_UNKNOWN          未知状态 0
   SIM_STATE_ABSENT           没插卡 1
   SIM_STATE_PIN_REQUIRED     锁定状态，需要用户的PIN码解锁 2
   SIM_STATE_PUK_REQUIRED     锁定状态，需要用户的PUK码解锁 3
   SIM_STATE_NETWORK_LOCKED   锁定状态，需要网络的PIN码解锁 4
   SIM_STATE_READY            就绪状态 5
   */
//    tm.getSimState();//int

  /*
   * 唯一的用户ID：
   * 例如：IMSI(国际移动用户识别码) for a GSM phone.
   * 需要权限：READ_PHONE_STATE
   */
//    tm.getSubscriberId();//String

  /*
   * 取得和语音邮件相关的标签，即为识别符
   * 需要权限：READ_PHONE_STATE
   */
//    tm.getVoiceMailAlphaTag();//String

  /*
   * 获取语音邮件号码：
   * 需要权限：READ_PHONE_STATE
   */
//    tm.getVoiceMailNumber();//String

  /*
   * ICC卡是否存在
   */
//    tm.hasIccCard();//boolean

  /*
   * 是否漫游:
   * (在GSM用途下)
   */
//    tm.isNetworkRoaming();//

}
