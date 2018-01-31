package gcg.testproject.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class DaJiaKanListBean {

    /**
     * type : 2
     * message : 操作成功!
     * data : {"time":1,"perlook":[{"lookid":343,"foruserid":76,"typeid":2,"lookvideo":null,"createtime":1498118430000,"trueintegral":21,"falseintegral":0,"uncertainintegral":0,"isfinish":null,"coverpic":"http://app.newtonapple.cn/zhangyiyanupload/common/2017-06-22/QV1498118430359_mid.jpg","trueintuse":null,"falseintuse":null,"uncertainintuse":null,"lookstatus":1,"isdel":0,"remarks":[{"remarkid":352,"lookid":343,"remarkuser":76,"remark":1,"remarktime":1498118434000,"isuse":1,"starnum":5,"isdel":0},{"remarkid":353,"lookid":343,"remarkuser":77,"remark":1,"remarktime":1498118448000,"isuse":1,"starnum":1,"isdel":0}],"pics":[{"picid":702,"lookid":343,"lookpic":"http://app.newtonapple.cn/zhangyiyanupload/common/2017-06-22/QV1498118430359_mid.jpg"}],"truepercent":"真100%","falsepercent":"仿0%","unpercent":"有一眼0%","ismy":0}]}
     * status : 1
     */

    private String type;
    private String message;
    private DataEntity data;
    private int status;

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class DataEntity {
        /**
         * time : 1
         * perlook : [{"lookid":343,"foruserid":76,"typeid":2,"lookvideo":null,"createtime":1498118430000,"trueintegral":21,"falseintegral":0,"uncertainintegral":0,"isfinish":null,"coverpic":"http://app.newtonapple.cn/zhangyiyanupload/common/2017-06-22/QV1498118430359_mid.jpg","trueintuse":null,"falseintuse":null,"uncertainintuse":null,"lookstatus":1,"isdel":0,"remarks":[{"remarkid":352,"lookid":343,"remarkuser":76,"remark":1,"remarktime":1498118434000,"isuse":1,"starnum":5,"isdel":0},{"remarkid":353,"lookid":343,"remarkuser":77,"remark":1,"remarktime":1498118448000,"isuse":1,"starnum":1,"isdel":0}],"pics":[{"picid":702,"lookid":343,"lookpic":"http://app.newtonapple.cn/zhangyiyanupload/common/2017-06-22/QV1498118430359_mid.jpg"}],"truepercent":"真100%","falsepercent":"仿0%","unpercent":"有一眼0%","ismy":0}]
         */

        private int time;
        private List<PerlookEntity> perlook;

        public void setTime(int time) {
            this.time = time;
        }

        public void setPerlook(List<PerlookEntity> perlook) {
            this.perlook = perlook;
        }

        public int getTime() {
            return time;
        }

        public List<PerlookEntity> getPerlook() {
            return perlook;
        }

        public static class PerlookEntity {
            /**
             * lookid : 343
             * foruserid : 76
             * typeid : 2
             * lookvideo : null
             * createtime : 1498118430000
             * trueintegral : 21
             * falseintegral : 0
             * uncertainintegral : 0
             * isfinish : null
             * coverpic : http://app.newtonapple.cn/zhangyiyanupload/common/2017-06-22/QV1498118430359_mid.jpg
             * trueintuse : null
             * falseintuse : null
             * uncertainintuse : null
             * lookstatus : 1
             * isdel : 0
             * remarks : [{"remarkid":352,"lookid":343,"remarkuser":76,"remark":1,"remarktime":1498118434000,"isuse":1,"starnum":5,"isdel":0},{"remarkid":353,"lookid":343,"remarkuser":77,"remark":1,"remarktime":1498118448000,"isuse":1,"starnum":1,"isdel":0}]
             * pics : [{"picid":702,"lookid":343,"lookpic":"http://app.newtonapple.cn/zhangyiyanupload/common/2017-06-22/QV1498118430359_mid.jpg"}]
             * truepercent : 真100%
             * falsepercent : 仿0%
             * unpercent : 有一眼0%
             * ismy : 0
             */

            private int lookid;
            private int foruserid;
            private int typeid;
            private String lookvideo;
            private long createtime;
            private int trueintegral;
            private int falseintegral;
            private int uncertainintegral;
            private Object isfinish;
            private String coverpic;
            private Object trueintuse;
            private Object falseintuse;
            private Object uncertainintuse;
            private int lookstatus;
            private int isdel;
            private String truepercent;
            private String falsepercent;
            private String unpercent;
            private int ismy;
            private List<RemarksEntity> remarks;
            private List<PicsEntity> pics;

            public void setLookid(int lookid) {
                this.lookid = lookid;
            }

            public void setForuserid(int foruserid) {
                this.foruserid = foruserid;
            }

            public void setTypeid(int typeid) {
                this.typeid = typeid;
            }

            public void setLookvideo(String lookvideo) {
                this.lookvideo = lookvideo;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public void setTrueintegral(int trueintegral) {
                this.trueintegral = trueintegral;
            }

            public void setFalseintegral(int falseintegral) {
                this.falseintegral = falseintegral;
            }

            public void setUncertainintegral(int uncertainintegral) {
                this.uncertainintegral = uncertainintegral;
            }

            public void setIsfinish(Object isfinish) {
                this.isfinish = isfinish;
            }

            public void setCoverpic(String coverpic) {
                this.coverpic = coverpic;
            }

            public void setTrueintuse(Object trueintuse) {
                this.trueintuse = trueintuse;
            }

            public void setFalseintuse(Object falseintuse) {
                this.falseintuse = falseintuse;
            }

            public void setUncertainintuse(Object uncertainintuse) {
                this.uncertainintuse = uncertainintuse;
            }

            public void setLookstatus(int lookstatus) {
                this.lookstatus = lookstatus;
            }

            public void setIsdel(int isdel) {
                this.isdel = isdel;
            }

            public void setTruepercent(String truepercent) {
                this.truepercent = truepercent;
            }

            public void setFalsepercent(String falsepercent) {
                this.falsepercent = falsepercent;
            }

            public void setUnpercent(String unpercent) {
                this.unpercent = unpercent;
            }

            public void setIsmy(int ismy) {
                this.ismy = ismy;
            }

            public void setRemarks(List<RemarksEntity> remarks) {
                this.remarks = remarks;
            }

            public void setPics(List<PicsEntity> pics) {
                this.pics = pics;
            }

            public int getLookid() {
                return lookid;
            }

            public int getForuserid() {
                return foruserid;
            }

            public int getTypeid() {
                return typeid;
            }

            public String getLookvideo() {
                return lookvideo;
            }

            public long getCreatetime() {
                return createtime;
            }

            public int getTrueintegral() {
                return trueintegral;
            }

            public int getFalseintegral() {
                return falseintegral;
            }

            public int getUncertainintegral() {
                return uncertainintegral;
            }

            public Object getIsfinish() {
                return isfinish;
            }

            public String getCoverpic() {
                return coverpic;
            }

            public Object getTrueintuse() {
                return trueintuse;
            }

            public Object getFalseintuse() {
                return falseintuse;
            }

            public Object getUncertainintuse() {
                return uncertainintuse;
            }

            public int getLookstatus() {
                return lookstatus;
            }

            public int getIsdel() {
                return isdel;
            }

            public String getTruepercent() {
                return truepercent;
            }

            public String getFalsepercent() {
                return falsepercent;
            }

            public String getUnpercent() {
                return unpercent;
            }

            public int getIsmy() {
                return ismy;
            }

            public List<RemarksEntity> getRemarks() {
                return remarks;
            }

            public List<PicsEntity> getPics() {
                return pics;
            }

            public static class RemarksEntity {
                /**
                 * remarkid : 352
                 * lookid : 343
                 * remarkuser : 76
                 * remark : 1
                 * remarktime : 1498118434000
                 * isuse : 1
                 * starnum : 5
                 * isdel : 0
                 */

                private int remarkid;
                private int lookid;
                private int remarkuser;
                private int remark;
                private long remarktime;
                private int isuse;
                private int starnum;
                private int isdel;

                public void setRemarkid(int remarkid) {
                    this.remarkid = remarkid;
                }

                public void setLookid(int lookid) {
                    this.lookid = lookid;
                }

                public void setRemarkuser(int remarkuser) {
                    this.remarkuser = remarkuser;
                }

                public void setRemark(int remark) {
                    this.remark = remark;
                }

                public void setRemarktime(long remarktime) {
                    this.remarktime = remarktime;
                }

                public void setIsuse(int isuse) {
                    this.isuse = isuse;
                }

                public void setStarnum(int starnum) {
                    this.starnum = starnum;
                }

                public void setIsdel(int isdel) {
                    this.isdel = isdel;
                }

                public int getRemarkid() {
                    return remarkid;
                }

                public int getLookid() {
                    return lookid;
                }

                public int getRemarkuser() {
                    return remarkuser;
                }

                public int getRemark() {
                    return remark;
                }

                public long getRemarktime() {
                    return remarktime;
                }

                public int getIsuse() {
                    return isuse;
                }

                public int getStarnum() {
                    return starnum;
                }

                public int getIsdel() {
                    return isdel;
                }
            }

            public static class PicsEntity {
                /**
                 * picid : 702
                 * lookid : 343
                 * lookpic : http://app.newtonapple.cn/zhangyiyanupload/common/2017-06-22/QV1498118430359_mid.jpg
                 */

                private int picid;
                private int lookid;
                private String lookpic;

                public void setPicid(int picid) {
                    this.picid = picid;
                }

                public void setLookid(int lookid) {
                    this.lookid = lookid;
                }

                public void setLookpic(String lookpic) {
                    this.lookpic = lookpic;
                }

                public int getPicid() {
                    return picid;
                }

                public int getLookid() {
                    return lookid;
                }

                public String getLookpic() {
                    return lookpic;
                }
            }
        }
    }
}
