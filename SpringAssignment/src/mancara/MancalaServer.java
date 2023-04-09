package mancara;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

// サーバ
public class MancalaServer {

	public static void main(String[] args) {
		// 受付開始
		try(ServerSocket server = new ServerSocket(50000)){
			while(true) {
				try {
					//接続を待ち受け
					System.out.println("Listening...");
					Socket sc = server.accept();
					
					// リーダ，ライタの準備
					System.out.println("Connected");
					BufferedReader reader = null;
					PrintWriter writer = null;
					Scanner keyboard = null;
					
					try {
						// InputStreamクラスをバイト→文字に変換し，BufferdReaderで効率化
						reader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
						// 既存のOutputStreamから新しいPrintWriterを作成．第2引数のtrueはprintlnで自動フラッシュ
						writer = new PrintWriter(sc.getOutputStream(), true);
						keyboard = new Scanner(System.in);
						
						// 先攻後攻を決め，通知する
						Random rnd = new Random();
						Board b; // ボードの作成
						if(rnd.nextInt(2) == 0) {
							writer.println("1"); // クライアントは先攻
							System.out.println("サーバは後攻です．");
							b = new Board(false); // サーバは後攻
						}else {
							writer.println("0"); // クライアントは後攻
							System.out.println("サーバは先攻です．");
							b = new Board(true); // サーバは先攻
						}
						
						// ゲーム
						Game g = new Game(b, reader, writer, keyboard);
						if (g.play() == 1) {
							System.out.printf("\n---EXIT---\n\n");
						}
												
					} catch(Exception e3) {
						e3.printStackTrace();
					} finally {
						// リソースの解放
						if (reader != null)
							reader.close();
						if (writer != null)
							writer.close();
						if (sc != null)
							sc.close();
					}			
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
					break;
				}
				
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		
	}

}

// ----------------------------
//	□	j	i	h	g	f	□	
//	□	--------=--------	□
//	□	1	3	0	3	2	□
//	□						□
//	□	1	2	3	0	0	□
//	□	-----------------	□
//	□	a	b	c	d	e	□
//	---------------------------