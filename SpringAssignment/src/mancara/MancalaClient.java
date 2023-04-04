package mancara;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MancalaClient {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 50000);
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// キーボード入力用のリーダーの作成
				BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in))){
			
			try {
				
				String line = reader.readLine();
				Board b; // ボードの作成
				
				if (line.equals("0")) {
					b = new Board(false); 
					System.out.println("クライアントは後攻です．");
				}else if (line.equals("1")) {
					b = new Board(true);
					System.out.println("クライアントは先攻です．");
				}else {
					throw new IllegalArgumentException("先攻/後攻の処理が異常終了しました");
				}
			
				// ゲーム
				Game g = new Game(b, reader, writer);
				if (g.play() == 1) {
					System.out.printf("\n---EXIT---\n\n");
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}