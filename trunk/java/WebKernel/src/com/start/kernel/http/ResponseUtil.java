package com.start.kernel.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.start.framework.utils.StackTraceInfo;
import com.start.kernel.config.ConfigParameter;
import com.start.kernel.utils.LogUtils;
import com.start.kernel.utils.PropertiesUtils;
import com.start.kernel.utils.StringUtils;
import com.start.kernel.utils.TimeUtils;

public class ResponseUtil {

	public static final String RESULT="result";
	
	public static final String INFO="info";
	public static final String CODE="code";
	public static final String MESSAGE="message";
	
	private static final String[] infoElements = new String[]{CODE,MESSAGE};
	
	private String[] infoContents = null;
	
	private OutputXML outputXML = null;
	
	private HttpServletResponse response = null;

	private String[] getInfoContents(Integer code, String message) {
		return new String[]{StringUtils.nullToUnknown(String.valueOf(code)), message};
	}

	public ResponseUtil(HttpServletResponse response) {
		this.response = response;
		outputXML = new OutputXML(response);
	}

	public void response(Integer code) {
		responseHeader(code);
		responseXML(code);
	}

	public void responseHeaders(Integer code, Map<String, String> header_data) {
		responseHeader(code);
		responseHeaders(header_data);
	}

	public void response(Integer code, String message) {
		responseHeader(code);
		responseXML(code, message);
	}

	public void response(Integer code, String[] subheads, String[] subelements, String[] subcontents) {
		responseHeader(code);
		responseXML(code, subheads, subelements, subcontents);
	}

	public void response(Integer code, String[] subheads, String[] subelements, String[][] subcontents) {
		responseHeader(code);
		responseXML(code, subheads, subelements, subcontents);
	}

	public void response(Integer code, String[][] heads, String[][] elements, String[][][] contents) {
		responseHeader(code);
		responseXML(code, heads, elements, contents);
	}

	public void response(Integer code, String message, String[] subheads, String[] subelements, String[] subcontents) {
		responseHeader(code);
		responseXML(code, message, subheads, subelements, subcontents);
	}

	public void responseHeader(int status) {
		if(status >= 600) {
			status = 403;
		}
		response.setStatus(status);
		response.setHeader("servertime", TimeUtils.getSysTimeLong());
		response.setHeader("serverversion", ConfigParameter.SYSTEMINFO_FULL);
	}
	/**
	 * 添加到响应头
	 */
	public void responseHeaders(Map<String, String> header_data) {
		if(header_data != null) {
	        List<String> keys = new ArrayList<String>(header_data.keySet());
			Collections.sort(keys);
			String key = "";
			String value = "";
			for (int i = 0; i < keys.size(); i++) {
				key = StringUtils.nullToStrTrim(keys.get(i));
				value = StringUtils.nullToStrTrim(header_data.get(key));
				response.setHeader(key, StringUtils.encode(value));
			}
		}
	}

	public void responseXML(Integer code) {
		String message = PropertiesUtils.getMessage(String.valueOf(code));
		responseXML(code, message);
	}

	public void responseXML(Integer code, String message) {
		infoContents = getInfoContents(code, message);
		outputXML.responseXML(RESULT, INFO, infoElements, infoContents);
	}

	public void responseXML(Integer code, String[] subheads, String[] subelements, String[] subcontents) {
		String message = PropertiesUtils.getMessage(String.valueOf(code));
		responseXML(code, message, subheads, subelements, subcontents);
	}

	public void responseXML(Integer code, String[] subheads, String[] subelements, String[][] subcontents) {
		String message = PropertiesUtils.getMessage(String.valueOf(code));
		responseXML(code, message, subheads, subelements, subcontents);
	}

	public void responseXML(Integer code, String[][] heads, String[][] elements, String[][][] contents) {
		String message = PropertiesUtils.getMessage(String.valueOf(code));
		responseXML(code, message, heads, elements, contents);
	}

	public void responseXML(Integer code, String message, String[] subheads, String[] subelements, String[] subcontents) {
		infoContents = getInfoContents(code, message);
		outputXML.responseXML(RESULT, new String[][]{new String[]{INFO}, subheads}, new String[][]{infoElements, subelements}, new String[][][]{new String[][]{infoContents},new String[][]{subcontents}});
	}

	public void responseXML(Integer code, String message, String[] subheads, String[] subelements, String[][] subcontents) {
		infoContents = getInfoContents(code, message);
		outputXML.responseXML(RESULT, new String[][]{new String[]{INFO}, subheads}, new String[][]{infoElements, subelements}, new String[][][]{new String[][]{infoContents},subcontents});
	}

	public void responseXML(Integer code, String message, String[][] heads, String[][] elements, String[][][] contents) {
		infoContents = getInfoContents(code, message);
		if(heads[0] == null) {
			heads[0] = new String[]{INFO};
		}
		if(elements[0] == null) {
			elements[0] = infoElements;
		}
		if(contents[0] == null) {
			contents[0] = new String[][]{infoContents};
		}
		outputXML.responseXML(RESULT, heads, elements, contents);
	}

}
class OutputXML {

	private HttpServletResponse response = null;

	public OutputXML(HttpServletResponse response) {
		this.response = response;
    }

    public String getXML(String root, String head, String element, String content) {
    	return getXMLDO(root, new String[][]{new String[]{head}}, new String[][]{new String[]{element}}, new String[][][]{new String[][]{new String[]{content}}});
    }
    
    public String getXML(String root, String head, String[] elements, String[] contents) {
    	return getXMLDO(root, new String[][]{new String[]{head}}, new String[][]{elements}, new String[][][]{new String[][]{contents}});
    }
    
    public String getXML(String root, String head, String[][] elements, String[][] contents) {
    	return getXMLDO(root, new String[][]{new String[]{head}},elements, new String[][][]{contents});
    }
    
    public String getXML(String root, String head, String[][] elements, String[][][] contents) {
    	return getXMLDO(root, new String[][]{new String[]{head}},elements, contents);
    }
    
    public String getXML(String root, String[] heads, String[] elements, String[][] contents) {
    	return getXMLDO(root, new String[][]{heads}, new String[][]{elements}, new String[][][]{contents});
    }
    
    public String getXML(String root, String[] heads, String[][] elements, String[][][] contents) {
    	return getXMLDO(root, new String[][]{heads}, elements, contents);
    }

	public String getXML(String root, String[][] heads, String[][] elements, String[][][] contents) {
		return getXMLDO(root, heads, elements, contents);
	}
	
    public void responseXML(HttpServletResponse response, String root, String head, String element, String content) {
    	responseXMLDO(response, root, new String[][]{new String[]{head}}, new String[][]{new String[]{element}}, new String[][][]{new String[][]{new String[]{content}}});
    }
   
    public void responseXML(HttpServletResponse response, String root, String head, String[] elements, String[] contents) {
    	responseXMLDO(response, root, new String[][]{new String[]{head}}, new String[][]{elements}, new String[][][]{new String[][]{contents}});
    }

    public void responseXML(HttpServletResponse response, String root, String head, String[][] elements, String[][] contents) {
    	responseXMLDO(response, root, new String[][]{new String[]{head}},elements, new String[][][]{contents});
    }

    public void responseXML(HttpServletResponse response, String root, String head, String[][] elements, String[][][] contents) {
    	responseXMLDO(response, root, new String[][]{new String[]{head}},elements, contents);
    }

    public void responseXML(HttpServletResponse response, String root, String[] heads, String[] elements, String[][] contents) {
    	responseXMLDO(response, root, new String[][]{heads}, new String[][]{elements}, new String[][][]{contents});
    }
    
    public void responseXML(HttpServletResponse response, String root, String[] heads, String[][] elements, String[][][] contents) {
    	responseXMLDO(response, root, new String[][]{heads}, elements, contents);
    }
    
    public void responseXML(String root, String head, String element, String content) {
    	responseXMLDO(root, new String[][]{new String[]{head}}, new String[][]{new String[]{element}}, new String[][][]{new String[][]{new String[]{content}}});
    }

    public void responseXML(String root, String head, String[] elements, String[] contents) {
    	responseXMLDO(root, new String[][]{new String[]{head}}, new String[][]{elements}, new String[][][]{new String[][]{contents}});
    }
    
    public void responseXML(String root, String head, String[][] elements, String[][] contents) {
    	responseXMLDO(root, new String[][]{new String[]{head}},elements, new String[][][]{contents});
    }
  
    public void responseXML(String root, String head, String[][] elements, String[][][] contents) {
    	responseXMLDO(root, new String[][]{new String[]{head}},elements, contents);
    }
    
    public void responseXML(String root, String[] heads, String[] elements, String[][] contents) {
    	responseXMLDO(root, new String[][]{heads}, new String[][]{elements}, new String[][][]{contents});
    }
    
    public void responseXML(String root, String[] heads, String[][] elements, String[][][] contents) {
    	responseXMLDO(root, new String[][]{heads}, elements, contents);
    }
    
	public void responseXML(String root, String[][] heads, String[][] elements, String[][][] contents) {
		responseXMLDO(root, heads, elements, contents);
	}

	public void responseXML(HttpServletResponse response, String root, String[][] heads, String[][] elements, String[][][] contents) {
		responseXMLDO(response, root, heads, elements, contents);
	}

	private void responseXMLDO(String rootStr, String[][] heads, String[][] elements, String[][][] contents) {
		responseXMLDO(response, rootStr, heads, elements, contents);
	}

	private void responseXMLDO(HttpServletResponse response, String rootStr, String[][] heads, String[][] elements, String[][][] contents) {
    	boolean hasHead;
		boolean hasSubHead;
		Element head = null;
		Element subhead = null;
		List<Element> list = null;
		Document doc = new Document();
		Element root = new Element(rootStr);
		doc.setRootElement(root);
		String element = "";
		for (int i = 0; i < heads.length; i++) {
			if(contents[i] == null) {
				continue;
			}
			hasHead = StringUtils.nullToStrTrim(heads[i][0]).length() > 0 ? true : false;
			hasSubHead = (heads[i].length >= 2 && StringUtils.nullToStrTrim(heads[i][1]).length() > 0) ? true : false;

			if (hasHead) {
				head = new Element(heads[i][0]);
				root.getChildren().add(head);
			}

			for (int j = 0; j < contents[i].length; j++) {
				list = new ArrayList<Element>();
				if(elements[i] == null) {
					continue;
				}
				for (int k = 0; k < elements[i].length; k++) {
					element = StringUtils.nullToStrTrim(elements[i][k]);
					if(StringUtils.isEmpty(element)) {
						continue;
					}
					list.add(new Element(element).setText(contents[i][j][k]));
				}

				if (hasSubHead) {
					subhead = new Element(heads[i][1]);
					if (hasHead) {
						head.getChildren().add(subhead);
					} else {
						root.getChildren().add(subhead);
					}
					subhead.getChildren().addAll(list);
				} else {
					if (hasHead) {
						head.getChildren().addAll(list);
					} else {
						root.getChildren().addAll(list);
					}
				}
			}
		}
		heads = null;
		elements = null;
		contents = null;
		Format format = Format.getPrettyFormat();
		if(ConfigParameter.SYSTEMFLAG) {
			format.setEncoding("utf-8");
			format.setIndent("");
			format.setLineSeparator("");
		}
		XMLOutputter xmlOutputter = new XMLOutputter(format);
		try {
			if(response != null) {
				response.setCharacterEncoding("UTF-8");
		    	response.setContentType("text/xml");
//				response.setHeader("Cache-Control", "no-cache");
				xmlOutputter.output(doc, response.getWriter());
				LogUtils.logInfo("输出XML：" + xmlOutputter.outputString(doc).trim());
			} else {
				xmlOutputter.output(doc, System.out);
			}
		} catch (IOException e) {
        	LogUtils.logError(StackTraceInfo.getTraceInfo() + "输出XML异常：" + StringUtils.nullToStrTrim(e.getMessage()));
		} finally {
			doc = null;
		}
	}

	private String getXMLDO(String rootStr, String[][] heads, String[][] elements, String[][][] contents) {
    	boolean hasHead;
    	boolean hasSubHead;
		Element head = null;
		Element subhead = null;
		List<Element> list = null;
    	Document doc = new Document();
    	Element root = new Element(rootStr);
    	doc.setRootElement(root);
    	String element = "";
		for (int i=0; i<heads.length; i++) {
			if(contents[i] == null) {
				continue;
			}
			hasHead = StringUtils.nullToStrTrim(heads[i][0]).length() > 0 ? true : false;
			hasSubHead = (heads[i].length >= 2 && StringUtils.nullToStrTrim(heads[i][1]).length() > 0) ? true : false;
    		if(hasHead) {
    			head = new Element(heads[i][0]);
				root.getChildren().add(head);
    		}
			for (int j=0; j<contents[i].length; j++) {
	    		list = new ArrayList<Element>();
				if(elements[i] == null) {
					continue;
				}
	    		for (int k=0; k<elements[i].length; k++) {
					element = StringUtils.nullToStrTrim(elements[i][k]);
					if(StringUtils.isEmpty(element)) {
						continue;
					}
	    			list.add(new Element(element).setText(contents[i][j][k]));
	    		}
				if (hasSubHead) {
	    			subhead = new Element(heads[i][1]);
	    			if (hasHead) {
						head.getChildren().add(subhead);
	    			} else {
	    				root.getChildren().add(subhead);
	    			}
					subhead.getChildren().addAll(list);
	    		} else {
	    			if(hasHead) {
	    				head.getChildren().addAll(list);
	    			} else {
	    				root.getChildren().addAll(list);
	    			}
	    		}
			}
    	}
		heads = null;
		elements = null;
		contents = null;
		Format format = Format.getPrettyFormat();
		if(ConfigParameter.SYSTEMFLAG) {
			format.setEncoding("utf-8");
			format.setIndent("");
//			format.setIndent("	");
			format.setLineSeparator("");
//			format.setLineSeparator("\r\n");
		}
		XMLOutputter xmlOutputter = new XMLOutputter(format);
		return xmlOutputter.outputString(doc).trim();
	}

}