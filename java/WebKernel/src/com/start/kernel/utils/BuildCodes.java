package com.start.kernel.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 模板生成类
 * @author Start
 */
public class BuildCodes{
	
	private String CODESPATH="/data/codes/";
	
	private String[] FILECODES={"Action","Service","ServiceImpl","Dao","DaoImpl","Entity"};
	
	private String[] FILETYPES={"action","service","service/impl","dao","dao/impl","entity"};
	/**
	 * 包路径
	 */
	private String packagepath;
	/**
	 * 包名全小写
	 */
	private String packageName;
	/**
	 * 完整的实体名,首字母大写
	 */
	private String fullEntityName;
	/**
	 * 实体名简写,首字母小写
	 */
	private String simpleEntityName;

	public BuildCodes(String packagepath,String packageName,String entityName){
		this.packagepath=packagepath;
		/**
		 * 默认包名为小写顾全转为小写
		 */
		this.packageName=packageName.toLowerCase();
		/**
		 * 把实体名首字母转为大写
		 */
		this.fullEntityName=entityName.substring(0,1).toUpperCase()+entityName.substring(1);
		/**
		 * 把实体名首字母转为小写
		 */
		this.simpleEntityName=entityName.substring(0,1).toLowerCase()+entityName.substring(1);
	}
	
	public void buildCodeFile() throws IOException{
		for(int i=0;i<FILETYPES.length;i++){
			/**
			 * 文本的内容
			 */
			StringBuilder txtContentBuilder=new StringBuilder();
			BufferedReader reader =new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(CODESPATH+FILECODES[i]+".txt")));
	        try {
	        	/**
	        	 * 每一行文本的内容
	        	 */
	        	String lineContent=new String();
	            while((lineContent=reader.readLine())!=null){
	            	txtContentBuilder.append(lineContent+"\n");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	        	reader.close();
	        }
	        writerFile(txtContentBuilder.toString(),FILETYPES[i],FILECODES[i]);
		}
	}
	/**
	 * @param content
	 * 文本内容
	 * @param packageType
	 * 生成的类型 action层;service层;service/impl层;dao层;dao/daoimpl层
	 * @param fileCode
	 * 所属的层类型
	 */
	void writerFile(String content,String builderType,String fileCode) throws IOException{
		String directory="src/"+packagepath+"/"+packageName+"/"+builderType.toLowerCase()+"/";
		/**
		 * 目录不存在则创建
		 */
		File codeDirectory=new File(directory);
		if(!codeDirectory.exists()){
			codeDirectory.mkdirs();
		}
		if(fileCode.equals("Entity"))fileCode="";
		File writerFile=new File(directory+fullEntityName+fileCode+".java");
		if(!writerFile.exists()){
			/**
			 * 表的前缀默认取包名的前三位
			 */
			String tabHeader=new String();
			if(packageName.length()>3){
				tabHeader=packageName.substring(0,3).toUpperCase();
			}else{
				tabHeader=packageName.toUpperCase();
			}
			content=content.
				replaceAll("@\\{PACKAGENAME\\}",packageName).					//包
				replaceAll("@\\{ENTITYNAME\\}", fullEntityName).			 	//实体
				replaceAll("@\\{SIMPLEENTITYNAME\\}", simpleEntityName). 		//实体简写
				replaceAll("@\\{UENTITYNAME\\}", fullEntityName.toUpperCase()).	//实体大字
				replaceAll("@\\{TABEXTESION\\}",tabHeader).						//实体数据表前缀
				replaceAll("@\\{COMPANYNAME\\}",packagepath.replace("/", "."));	//实体数据表前缀
			writerFile.createNewFile();
			BufferedWriter writer=new BufferedWriter(new FileWriter(writerFile));
			writer.write(content);
			writer.flush();
			writer.close();
			System.out.println(fullEntityName+fileCode+".java 源文件创建成功！");
		}else{
			System.out.println(fullEntityName+fileCode+".java  源文件已经存在创建失败！");
		}
	}
	
	public static void main(String[] args) throws IOException{
		BuildCodes buildCodeTemplates=new BuildCodes("org/androidpn","service","ApnUser");
		buildCodeTemplates.buildCodeFile();
	}
	
}