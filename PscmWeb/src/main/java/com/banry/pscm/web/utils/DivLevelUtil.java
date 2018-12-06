/**
 * 
 */
package com.banry.pscm.web.utils;

/**
 * @author Xu Dingkui
 * @date 2017年12月17日
 */
public class DivLevelUtil {

	public enum EnumDivLevel {
		DivLevel1(1, "单项工程"),DivLevel2(2, "子单项工程"), DivLevel3(3, "单位工程"), DivLevel4(4, "子单位工程"),
		DivLevel5(5, "分部工程"), DivLevel6(6, "子分部工程"), DivLevel7(7, "分项工程"), DivLevel8(8, "子分项工程"),
		DivLevel9(9, "工序"), DivLevel10(10, "子工序");
		private Integer key;
		private String value;
		//自定义的构造函数，参数数量，名字随便自己取
		//构造器默认也只能是private, 从而保证构造函数只能在内部使用 
		private EnumDivLevel(Integer key, String value)
		{
			this.key = key;
			this.value = value;
		}

		public Integer getKey() {
			return key;
		}


		public void setKey(Integer key) {
			this.key = key;
		}


		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}
		//重新toString方法，默认的toString方法返回的就是枚举变量的名字，和name()方法返回值一样
		@Override
		public String toString()
		{
			return this.key+":"+this.value;
			
		}
	}
	
	public static String getLevelValue(Integer level) {
		for (EnumDivLevel aLight : EnumDivLevel.values ()) {
			if (level.intValue() == aLight.key.intValue()) {
				return aLight.value;
			}
       }
		return "";
	}
}