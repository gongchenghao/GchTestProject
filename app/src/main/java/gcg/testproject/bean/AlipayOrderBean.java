package gcg.testproject.bean;

/**
 * 作者: 宫成浩
 * 日期: 2018/1/25.
 */


public class AlipayOrderBean {

    /**
     * code : 1
     * msg : 下单成功！
     * payment : {"code":"1","data":"app_id=2016093002017342&biz_content=%7B%22total_amount%22%3A%226380.00%22%2C%22body%22%3A%22%E5%B8%86%E8%88%B9%E5%A4%8F%E4%BB%A4%E8%90%A5%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%B8%86%E8%88%B9%E5%A4%8F%E4%BB%A4%E8%90%A5%22%2C%22seller_id%22%3A%222088421370251733%22%2C%22out_trade_no%22%3A%2218012515310001%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.customer.xinyi8090.cn%2Fxinyi_interfice%2Fcallbacks.do&sign_type=RSA&timestamp=2018-01-25+15%3A31%3A08&version=1.0&sign=mVIzGdaxR88WD4t%2BcbDDcOiy5a%2FzSwoBPFN9qnbxwk20SijIHpQORR1rteW4f%2BDWhQmLnzGvdGquCbV5Yox%2FYLeQLvBSew%2BmLh1GCHWtG2ldsW9ZKQvlphzzhtBJp7BwKvZmrzy%2FUZ0SgD5HBfMmxlBhMyOaf%2FY01MAbV%2F15mS8%3D","msg":"支付成功"}
     * datas : {"androidpath":"http://home.console.xinyi8090.cn/files/01/19/ed71e90.apk","map":{},"orderId":1661,"order_code":"18012515310001","usecode":"77529495"}
     */

    private String code;
    private String msg;
    private PaymentBean payment;
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

    public PaymentBean getPayment() {
        return payment;
    }

    public void setPayment(PaymentBean payment) {
        this.payment = payment;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class PaymentBean {
        /**
         * code : 1
         * data : app_id=2016093002017342&biz_content=%7B%22total_amount%22%3A%226380.00%22%2C%22body%22%3A%22%E5%B8%86%E8%88%B9%E5%A4%8F%E4%BB%A4%E8%90%A5%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%B8%86%E8%88%B9%E5%A4%8F%E4%BB%A4%E8%90%A5%22%2C%22seller_id%22%3A%222088421370251733%22%2C%22out_trade_no%22%3A%2218012515310001%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.customer.xinyi8090.cn%2Fxinyi_interfice%2Fcallbacks.do&sign_type=RSA&timestamp=2018-01-25+15%3A31%3A08&version=1.0&sign=mVIzGdaxR88WD4t%2BcbDDcOiy5a%2FzSwoBPFN9qnbxwk20SijIHpQORR1rteW4f%2BDWhQmLnzGvdGquCbV5Yox%2FYLeQLvBSew%2BmLh1GCHWtG2ldsW9ZKQvlphzzhtBJp7BwKvZmrzy%2FUZ0SgD5HBfMmxlBhMyOaf%2FY01MAbV%2F15mS8%3D
         * msg : 支付成功
         */

        private String code;
        private String data;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "PaymentBean{" +
                    "code='" + code + '\'' +
                    ", data='" + data + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    public static class DatasBean {
        /**
         * androidpath : http://home.console.xinyi8090.cn/files/01/19/ed71e90.apk
         * map : {}
         * orderId : 1661
         * order_code : 18012515310001
         * usecode : 77529495
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

        @Override
        public String toString() {
            return "DatasBean{" +
                    "androidpath='" + androidpath + '\'' +
                    ", map=" + map +
                    ", orderId=" + orderId +
                    ", order_code='" + order_code + '\'' +
                    ", usecode='" + usecode + '\'' +
                    '}';
        }
    }
}
