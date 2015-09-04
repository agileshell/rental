package com.insoul.rental.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Printer {

	public static void printWord(String path) {
		ComThread.InitSTA();
		ActiveXComponent wordCom = new ActiveXComponent("Word.Application");
		Dispatch wrdDocs = null;
		try {
			// wordCom.setProperty(propertyName, propValue);
			// 设置当前使用的打印机，我的Adobe Distiller打印机名字为"Adobe PDF"
			wordCom.setProperty("ActivePrinter", new Variant("Adobe PDF"));

			Dispatch.put(wordCom, "Visible", new Variant(true));
			wrdDocs = wordCom.getProperty("Documents").toDispatch();
			// 打开文档
			Dispatch word = Dispatch.call(wrdDocs, "Open", path).toDispatch();
			// 开始打印
			Dispatch.get(word, "PrintOut");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wrdDocs != null)
					Dispatch.call(wrdDocs, "Close", new Variant(0));
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 始终释放资源
			ComThread.Release();
		}
	}
}
