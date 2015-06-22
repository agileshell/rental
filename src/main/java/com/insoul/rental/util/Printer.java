package com.insoul.rental.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Printer {

    public static void printWord(String path) {
        ComThread.InitSTA();
        ActiveXComponent wordCom = new ActiveXComponent("Word.Application");
        try {
            Dispatch.put(wordCom, "Visible", new Variant(true));
            Dispatch wrdDocs = wordCom.getProperty("Documents").toDispatch();
            // 打开文档
            Dispatch excel = Dispatch.call(wrdDocs, "Open", path).toDispatch();
            // 开始打印
            Dispatch.get(excel, "PrintOut");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 始终释放资源
            ComThread.Release();
        }
    }
}
