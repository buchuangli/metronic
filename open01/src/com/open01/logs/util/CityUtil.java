package com.open01.logs.util;

public enum CityUtil {
	BJ("北京","Beijing"), SH("上海", "Shanghai"), TJ("天津","Tianjin"),
	cq("重庆", "Chongqing"), ah("安徽","Anhui"),xg("香港","Xianggang"), am("澳门", "Aomen"),
	fj("福建", "Fujian"), gd("广东","Guangdong"), gx("广西", "Guangxi"), gz("贵州","Guizhou"),
	gs("甘肃", "Gansu"), hn("海南","Hainan"), hb("河北", "Hebei"), hen("河南","Henan"),
	hlj("黑龙江", "Heilongjiang"), hub("湖北","Hubei"), hun("湖南", "Hunan"), jl("吉林","Jilin"),
	js("江苏", "Jiangsu"), jx("江西","Jiangxi"), ln("辽宁", "Liaoning"), nmg("内蒙古","Neimenggu"),
	nx("宁夏", "Ningxia"), qh("青海","Qinghai"), sx("陕西", "Shanxi"), shx("山西","Shanxi"),
	sd("山东", "Shandong"), sc("四川","Sichuan"), tw("台湾", "Taiwan"), xz("西藏","Xizang"),
	xj("新疆", "Xinjiang"), yn("云南","Yunnan"), zj("浙江", "Zhejiang"),;
    // 成员变量
    private String name;
    private String index;

    // 构造方法
    private CityUtil(String name, String index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(String index) {
        for (CityUtil c : CityUtil.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
