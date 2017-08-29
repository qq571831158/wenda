package com.newcoder.wenda.service;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2017/8/29.
 */
@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bis = new BufferedReader(isr);
            String lineText;
            while ((lineText = bis.readLine()) != null){
                addWord(lineText.trim());
            }
            bis.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败",e.getMessage());
        }
    }

    //增加关键词
    private void addWord(String lineText) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineText.length(); i++) {
            Character c = lineText.charAt(i);
            if (isSymbol(c)){
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
            if (i == lineText.length() - 1) {
                tempNode.setkeywordEnd(true);
            }
        }
    }

    private class TrieNode {
        //是否关键词截尾
        private boolean end = false;
        //当前节点下的所有子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        public TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return end;
        }

        void setkeywordEnd(boolean end) {
            this.end = end;
        }
    }



    private TrieNode rootNode = new TrieNode();

    private boolean isSymbol(char c){
        int ic = (int)c;
        //东亚文字在2E80到9FFF之间
        return !CharUtils.isAsciiAlphanumeric(c) &&(ic<0x2E80 || ic>0x9FFF);
    }


    public String filter(String text){
        if (StringUtils.isBlank(text))
            return text;
        StringBuilder sb = new StringBuilder();
        String replacement = "***";
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        while (position<text.length()){
            char c = text.charAt(position);
            if (isSymbol(c)){
                if (tempNode == rootNode){
                    sb.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null){
                sb.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd()){
                //发现敏感词
                sb.append(replacement);
                position = position + 1;
                begin = position;
                tempNode =rootNode;
            }else {
                ++position;
            }

        }
        sb.append(text.substring(begin));
        return sb.toString();
    }

    public static void main(String[] args){
        SensitiveService s= new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        System.out.println(s.filter("你好色 情"));
    }


}
