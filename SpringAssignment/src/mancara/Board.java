package mancara;

public class Board{
	public boolean isFirst; //先攻ならtrue
	private int pocketNum = 4; //ポケットの数(両者の合計)
	private int stoneNum = 4; //初期盤面の石の数
	
	private int[] stones = new int[pocketNum]; //各ポケットの石の数
	
	public Board(boolean b) {
		isFirst = b;
		clear();
	}
	
	// 盤面を初期状態にリセットする
	public void clear() {
		for(int i=0; i<pocketNum; i++) {
			stones[i] = stoneNum;
		}
	}
	
	// 現在の盤面を表示
	public void show() {
		
		System.out.printf("\n");
		// 相手陣の盤面の表示
		for(int i=0; i<pocketNum/2+2; i++) {
			System.out.print("----");
		}
		System.out.printf("\n");
		
		int pocket;
		if(isFirst) {
			pocket = pocketNum - 1;
		}else {
			pocket = pocketNum/2 - 1;
		}
		
		System.out.printf("□	");
		for(int i=0; i<pocketNum/2; i++) {
			System.out.printf("%c\t", pocket - i + 97);
		}
		System.out.printf("□\n");
		
		System.out.printf("□	");
		for(int i=0; i<pocketNum/2-1; i++) {
			System.out.print("----");
		}
		System.out.printf("-	□\n");
		
		System.out.printf("□	");
		for(int i=0; i<pocketNum/2; i++) {
			System.out.printf("%d\t", stones[pocket - i]);
		}
		System.out.printf("□\n□");
		for(int i=0; i<pocketNum/2 + 1; i++) {
			System.out.printf("\t");
		}
		System.out.printf("□\n");
		
		// 自陣の盤面の表示
		if(isFirst) {
			pocket = 0;
		}else {
			pocket = pocketNum/2;
		}
		System.out.printf("□	");
		for(int i=0; i<pocketNum/2; i++) {
			System.out.printf("%d\t", stones[pocket + i]);
		}
		System.out.printf("□\n");
		
		System.out.printf("□	");
		for(int i=0; i<pocketNum/2-1; i++) {
			System.out.print("----");
		}
		System.out.printf("-	□\n");
		
		System.out.printf("□	");
		for(int i=0; i<pocketNum/2; i++) {
			System.out.printf("%c\t", pocket + i + 97);
		}
		System.out.printf("□\n");
		
		
		for(int i=0; i<pocketNum/2+2; i++) {
			System.out.print("----");
		}
		System.out.printf("\n\n");
	}
	
	
	// ポケット番号を受け取り，石を動かす
	public int move(char pocket, boolean f) {
		int p = pocket - 97; // 注目するポケットのインデックス
		if(f && (p < 0 || p >= pocketNum/2) || !f &&(p < pocketNum/2 || p > pocketNum)) {
			return -1; // エラー:ポケット名が不正
		}
		
		int i = stones[p]; // 動かす石の残り数
		if(i < 1) {
			return -2; // エラー;選択できないポケット
		}
		
		stones[p] = 0; // 選択されたポケットの石を0にする
		while(i > 0) {
			p ++;
			if(p == pocketNum) {
				p = -1;
			}else if(p == pocketNum/2) {
				if(i == 1) {
					if(f) {
						return 1; // 追加ターン
					}else {
						return 0; // ターン終了
					}
				}else {
					i --;
					stones[p] ++;
				}
			}else {
				stones[p] ++;
			}
			i --;
		}
		
		if(p == -1 && !f) {
			return 1; // 追加ターン
		}else {
			return 0; // ターン終了
		}
		
	}
	
	// デバッグ用．ポケットpの石をi個にする
	public void set(char p, int i) {
		stones[p-97] = i;
	}
	
	// ゲームが終了したかどうかを判定する
	public boolean finished(boolean f) {
		if(f) {
			for(int i=0; i<pocketNum/2; i++) {
				if(stones[i] > 0) {
					return false; // 自陣に石が1つでも残っていたらfalse
				}
			}
		}else {
			for(int i=pocketNum/2; i<pocketNum; i++) {
				if(stones[i] > 0) {
					return false; // 自陣に石が1つでも残っていたらfalse
				}
			}
		}
		return true; // ループが回りきればtrue
	}
}