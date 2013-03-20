package com.start.kernel.utils;

/**
 * 权限授权算法
 * @author Start
 */
public class ACL {
	/**
	 * 表示授权状态，存储在数据库中为32bit的整型变量，可能的值有所有的整数
	 * 例:
	 * 1----0001:C
	 * 2----0010:R
	 * 4----0100:U
	 * 8----1000:D
	 */
	private int aclState;
	/**
     * 获取当前授权状态值
     */
    public int getAclState(){
    	return aclState;
    }
    /**
     * 设定总值
     */
    public void setAuthorizeTotalValue(int authorize){
    	aclState = 1 | authorize;
    }
	/**
     * acl实例跟主体和资源关联（实际上就是设置aclState的值）
     * 针对此实例进行授权：某种操作是否允许
     * @param permission
     * 只可以取值0,1,2,3
     * @param flag
     * true表示允许,false表示不允许（添加还是不添加这其中某个操作C/R/U/D）
     *  即将传入的permission设置到aclState的值中：添加某操作还是去除某操作。
	 *	定义临时变量tmp=1，然后tmp左移permission位（0、1、2、3位）
	 *	添加的话：aclState |= tmp;  即aclState与tmp作或（|）运算并返回值
	 *	去除的话：aclState &= ~tmp;  即aclState与tmp取反后作与（&）运算并返回值
	 *	例如：
	 *	若原来aclState的值为12（二进制即位1100，含有U和D操作），现在若要添加R（1）
	 *		1．  tmp（0001）左移1位，即现在tmp为0010
	 *		2．  aclState（1100）与tmp作或运算，即1100 | 0010 ，结果是1110，即为添加R操作后aclState的值了（十进制为13，加上了R操作）
	 *			   若原来aclState的值为12（二进制即位1100，含有U和D操作），现在若要去掉已有的U（2）
	 *		1．  tmp（0001）左移2位，即现在tmp为0100
	 *		2．  tmp取反后~tmp为1011，aclState（1100）与1011做和运算，即1100 & 1011 ，结果为1000，
	 *			   即为去掉了U操作aclState的值（十进制为8，去掉了U操作）
     */
    public void setAuthorize(int authorize, boolean flag){
       int tmp = 1;
       tmp = tmp << authorize;
       if(flag){
           aclState |= tmp;
       }else{
           aclState &= ~tmp;
       }
    }
    /**
     * 获得ACL授权（根据permission判断aclState是否含有某个操作C/R/U/D）
     * @param permission 一个整型的权限权
     * @return 授权标识：允许/不允许/不确定
     * 即根据permission判断aclState是否含有某个操作C/R/U/D），返回 允许/不允许/不确定 的整型值
	 *		  1.若aclTriState为-1，表示用户的授权时继承自角色的，故其是否含有permission操作不确定ACL_NEUTRAL；
	 *		  2.若aclTriState不为-1，表示用户的权限是直接授予的。定义临时变量tmp=1，将其左移permission位，并与aclState作与运算，判断其值是否为0：若不为0，则表示原aclState中含有传入的permission操作；若为0，则表示原aclState中不含有传入的permission操作。
	 *	例如：
	 *	若原来aclState的值为12（二进制即位1100，含有U和D操作），现在若传入的permission为2（即判断是否含有U操作），且是继承的。
	 *		  1.tmp（0001）左移2位，即现在tmp为0100
	 *		  2.tmp与aclState（1100）作与运算，即1100 & 0100 ， 结果为0100（十进制为4），不为0，故此断定：值为12的aclState必含有U（2）操作。
	 *	若原来aclState的值为12（二进制即位1100，含有U和D操作），现在若传入的permission为1（即判断是否含有R操作），且是继承的。
	 *	      1.tmp（0001）左移1位，即现在tmp为0010
	 *		  2.tmp与aclState（1100）作与运算，即1100 & 0010 ， 结果为0000（十进制为0），为0，故此断定：值为12的aclState必不含有R（1）操作。
     */
    public boolean isAuthorize(int authorize){
       int tmp = 1;
       tmp = tmp << authorize;
       tmp &= aclState;
       return tmp != 0;
    }
    
	public static void main(String[] args) {
		ACL acl=new ACL();
		acl.setAuthorizeTotalValue(5);
		acl.setAuthorize(2, true);
//		acl.setAuthorize(2, true);
//		acl.setAuthorize(2, true);
//		acl.setAuthorize(255, true);
//		acl.setAuthorizeTotalValue(-2147483644);
		System.out.println(acl.getAclState());
//		System.out.println(acl.getAuthorize(2));
	}
	
}