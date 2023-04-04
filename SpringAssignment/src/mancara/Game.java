package mancara;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

// 1つのゲームを進行する
public class Game {

	Board b;
	BufferedReader reader;
	PrintWriter writer;
	Scanner keyboard = new Scanner(System.in);
	boolean nowPlaying;
	
	public Game(Board board, BufferedReader r, PrintWriter w, Scanner k) {
		b = board;
		reader = r;
		writer = w;
		keyboard = k;
		nowPlaying = b.isFirst;
	}
	
	public int play() {
		int result;
		String nextMove = null;
		String line = null;
		
		System.out.printf("Game start\n\n");
		b.show();
		
		while (true) {
			if (nowPlaying) {
				System.out.print("Enter the next move: ");
				while (true) {
					try {
						nextMove = keyboard.next(); // キーボードから手番を入力
						
						if (nextMove.equals("EXIT")) {
							writer.println(nextMove);
							return 1;
						}
						
						result = b.move(nextMove.charAt(0), b.isFirst);
						
						// move()の返り値によって次の操作を決める
						if (result == 0) {
							writer.println(nextMove);
							break;
						}else if (result == 1) {
							writer.println(nextMove);
							b.show();
							
							if(b.finished(b.isFirst)) {
								System.out.printf("You win\n\n");
								return 0;
							}
							System.out.print("Enter another move: ");
						}else if (result == -1) {
							System.out.print("<Error> Please select your own pocket:  ");
						}else if (result == -2) {
							System.out.print("<Error> Do not select empty pocket:  ");
						}
					} catch (Exception e) {
						e.printStackTrace();
						return -1;
					}
				}
				b.show();
				
				if(b.finished(b.isFirst)) {
					System.out.printf("You win\n\n");
					return 0;
				}
				
				nowPlaying = false;
			} else {
				while (true) {
					try {
						System.out.printf("Waiting...\n\n");
						line = reader.readLine();
						if (line.equals("EXIT")) {
							System.out.println("Connection closed");
							return 1;
						}
						System.out.printf("The opponent chose %s.\n", line);
						if (b.move(line.charAt(0), !b.isFirst) == 0) {
							b.show();
							if(b.finished(!b.isFirst)) {
								System.out.printf("You lose\n\n"); // 押し出しで負け
								return 0;
							}
							nowPlaying = true;
							break;
						}
						if(b.finished(!b.isFirst)) {
							System.out.printf("\nYou lose\n\n"); // ラスト1つをポケットに入れられて負け
							return 0;
						}
					} catch(Exception e) {
						e.printStackTrace();
						return -1;
					}
					b.show();
				}
				
				if(b.finished(!b.isFirst) == true) {
					System.out.println("You lose");
					return 0;
				}
			}
		}
	}
}
