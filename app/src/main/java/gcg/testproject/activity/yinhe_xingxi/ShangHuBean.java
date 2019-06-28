package gcg.testproject.activity.yinhe_xingxi;

import java.util.List;

/**
 * discription:
 * Created by Guo WenHui on 2018/3/21.
 * phone:13552163255
 */

public class ShangHuBean {

	/**
	 * status : 1
	 * data : [{"id":"1","sh_name":"发发","sh_jc":"发发","star_id":"1","hb_x":2,"hb_y":"14","user":[{"uid":"10001","name":"郭小康","headaddress":"/resources/user_photo/10001/1521513504","time":"14:10"},{"uid":"10003","name":"宫成浩","headaddress":"/resources/user_photo/10003/1521775398","time":"14:10"}]}]
	 * errMsg : 成功
	 */

	private int status;
	private String errMsg;
	private List<DataBean> data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}

	public static class DataBean {
		/**
		 * id : 1
		 * sh_name : 发发
		 * sh_jc : 发发
		 * star_id : 1
		 * hb_x : 2
		 * hb_y : 14
		 * user : [{"uid":"10001","name":"郭小康","headaddress":"/resources/user_photo/10001/1521513504","time":"14:10"},{"uid":"10003","name":"宫成浩","headaddress":"/resources/user_photo/10003/1521775398","time":"14:10"}]
		 */

		private String shid;
		private String sh_name;
		private String sh_jc;

		public String getCws() {
			return cws;
		}

		public void setCws(String cws) {
			this.cws = cws;
		}

		private String cws;
		private String star_id;
		private int hb_x;
		private String hb_y;
		private List<UserBean> user;

		public String getId() {
			return shid;
		}

		public void setId(String shid) {
			this.shid = shid;
		}

		public String getSh_name() {
			return sh_name;
		}

		public void setSh_name(String sh_name) {
			this.sh_name = sh_name;
		}

		public String getSh_jc() {
			return sh_jc;
		}

		public void setSh_jc(String sh_jc) {
			this.sh_jc = sh_jc;
		}

		public String getStar_id() {
			return star_id;
		}

		public void setStar_id(String star_id) {
			this.star_id = star_id;
		}

		public int getHb_x() {
			return hb_x;
		}

		public void setHb_x(int hb_x) {
			this.hb_x = hb_x;
		}

		public String getHb_y() {
			return hb_y;
		}

		public void setHb_y(String hb_y) {
			this.hb_y = hb_y;
		}

		public List<UserBean> getUser() {
			return user;
		}

		public void setUser(List<UserBean> user) {
			this.user = user;
		}

		public static class UserBean {
			/**
			 * uid : 10001
			 * name : 郭小康
			 * headaddress : /resources/user_photo/10001/1521513504
			 * time : 14:10
			 */

			private String uid;
			private String name;
			private String headaddress;
			private String time;

			public String getUid() {
				return uid;
			}

			public void setUid(String uid) {
				this.uid = uid;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getHeadaddress() {
				return headaddress;
			}

			public void setHeadaddress(String headaddress) {
				this.headaddress = headaddress;
			}

			public String getTime() {
				return time;
			}

			public void setTime(String time) {
				this.time = time;
			}
		}
	}
}
