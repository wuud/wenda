package cn.wenda.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 敏感词过滤处理
 * 
 * @author wuu 2018年12月14日
 */
@Service
public class SensitiveWordService implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		rootNode = new TrieNode();

        try {
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	// 根节点
	private TrieNode rootNode = new TrieNode();

	/**
	 * 判断当前字符是否是一个符号
	 * 
	 * @author wuu 2018年12月14日
	 */
	private boolean isSymbol(char c) {
		int ic = (int) c;
		// 0x2E80-0x9FFF 东亚文字范围
		//当前字符如果不是美国字符并且不是东南亚字符就返回false
		return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
	}
	/**
	 *	 敏感词过滤算法
	 * @author wuu
	 * 2018年12月14日
	 */
	public String filter(String text) {
		if(StringUtils.isBlank(text))
			return text;
		String replacement="***";
		StringBuffer result=new StringBuffer();
		
		//数的遍历指针
		TrieNode tempNode=rootNode;
		int begin=0;//滚动指针
		int position=0;//定位指针
		
		while(position<text.length()) {
			char c=text.charAt(position);
			if(isSymbol(c)) {
				if(tempNode==rootNode) {
					result.append(c);
					++begin;
				}
				++position;
				continue;
			}
			tempNode=tempNode.getSubNode(c);
			// 当前位置的匹配结束
            if (tempNode == null) {
                // 以begin开始的字符串不存在敏感词
                result.append(text.charAt(begin));
                // 跳到下一个字符开始测试
                position = begin + 1;
                begin = position;
                // 回到树初始节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 发现敏感词， 从begin到position的位置用replacement替换掉
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                ++position;
            }
        }

		result.append(text.substring(begin));	
		
		return result.toString();
	}
	/**
	 * 为自己的树添加敏感词
	 * @param lineTxt
	 */
	 private void addWord(String lineTxt) {
	        TrieNode tempNode = rootNode;
	        // 循环每个字节
	        for (int i = 0; i < lineTxt.length(); ++i) {
	            Character c = lineTxt.charAt(i);
	            // 过滤空格
	            if (isSymbol(c)) {
	                continue;
	            }
	            TrieNode node = tempNode.getSubNode(c);

	            if (node == null) { // 没初始化
	                node = new TrieNode();
	                tempNode.addSubNode(c, node);
	            }

	            tempNode = node;

	            if (i == lineTxt.length() - 1) {
	                // 关键词结束， 设置结束标志
	                tempNode.setKeywordEnd(true);
	            }
	        }
	 }
	class TrieNode {
		private boolean end = false;
		private Map<Character, TrieNode> subNodes = new HashMap<>();

		/**
		 * 向指定位置添加节点树
		 * 
		 * @param c
		 * @param node
		 */
		void addSubNode(Character c, TrieNode node) {
			subNodes.put(c, node);
		}

		TrieNode getSubNode(Character c) {
			return subNodes.get(c);
		}

		boolean isKeywordEnd() {
			return end;
		}

		void setKeywordEnd(boolean end) {
			this.end = end;
		}

		public int getSubNodeCount() {
			return subNodes.size();
		}
	}
}
