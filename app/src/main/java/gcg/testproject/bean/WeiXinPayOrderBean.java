package gcg.testproject.bean;

/**
 * 作者: 宫成浩
 * 日期: 2018/1/25.
 */


public class WeiXinPayOrderBean {

    /**
     * code : 1
     * msg : 下单成功！
     * payment : {"code":"1","msg":"查询成功！","datas":{"appid":"wxda5a31f867e9c122","noncestr":"KMqySSPyqDayvmQZ","pack":"Sign=WXPay","partnerid":"1418263602","prepayid":"wx20180125165559c5cff837c50055423175","sign":"1DAE7F973C3E5F9FA0B3D7A5FC99CCB6","timestamp":"1516870559"}}
     * datas : {"androidpath":"http://home.console.xinyi8090.cn/files/01/19/ed71e90.apk","map":{},"orderId":1668,"order_code":"18012516550001","usecode":"17354887"}
     */

    private String code;
    private String msg;
    private PaymentBean payment;
    private DatasBeanX datas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PaymentBean getPayment() {
        return payment;
    }

    public void setPayment(PaymentBean payment) {
        this.payment = payment;
    }

    public DatasBeanX getDatas() {
        return datas;
    }

    public void setDatas(DatasBeanX datas) {
        this.datas = datas;
    }

    public static class PaymentBean {
        /**
         * code : 1
         * msg : 查询成功！
         * datas : {"appid":"wxda5a31f867e9c122","noncestr":"KMqySSPyqDayvmQZ","pack":"Sign=WXPay","partnerid":"1418263602","prepayid":"wx20180125165559c5cff837c50055423175","sign":"1DAE7F973C3E5F9FA0B3D7A5FC99CCB6","timestamp":"1516870559"}
         */

        private String code;
        private String msg;
        private DatasBean datas;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public DatasBean getDatas() {
            return datas;
        }

        public void setDatas(DatasBean datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * appid : wxda5a31f867e9c122
             * noncestr : KMqySSPyqDayvmQZ
             * pack : Sign=WXPay
             * partnerid : 1418263602
             * prepayid : wx20180125165559c5cff837c50055423175
             * sign : 1DAE7F973C3E5F9FA0B3D7A5FC99CCB6
             * timestamp : 1516870559
             */

            private String appid;
            private String noncestr;
            private String pack;
            private String partnerid;
            private String prepayid;
            private String sign;
            private String timestamp;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPack() {
                return pack;
            }

            public void setPack(String pack) {
                this.pack = pack;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

        }
    }

    public static class DatasBeanX {
        /**
         * androidpath : http://home.console.xinyi8090.cn/files/01/19/ed71e90.apk
         * map : {}
         * orderId : 1668
         * order_code : 18012516550001
         * usecode : 17354887
         */

        private String androidpath;
        private MapBean map;
        private int orderId;
        private String order_code;
        private String usecode;

        public String getAndroidpath() {
            return androidpath;
        }

        public void setAndroidpath(String androidpath) {
            this.androidpath = androidpath;
        }

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getUsecode() {
            return usecode;
        }

        public void setUsecode(String usecode) {
            this.usecode = usecode;
        }

        public static class MapBean {
        }
    }
}
