// Gustavs Brizga 211RDB356 10.grupa
import java.util.ArrayList;
import java.util.List;

public class Node { //Spēles koka klase
    int val; // glabā katras virsotnes vērtību
    int minimaxScore = 0; //glabā katras virsotnes minimax novērtējumu
    List<Node> children; //glabā datus par katras virsotnes pēctečiem

    Node(int val) {
        this.val = val;
        this.minimaxScore = 0;
        this.children = new ArrayList<>();
    }
    //Pievieno zaru ar pēcteci
    void addChild(int value) {
        Node child = new Node(value);
        this.children.add(child);
    }
    //Rekursīvi ģenerē spēles koku visiem iespējamajiem gājieniem
    void generateTree() {
        int[] moves = { 2, 5, 7 };

        //Ģenerē tālāk tikai tad, ja spēli ir iespējams turpināt.
        if (this.val < 17 && this.val != 14 && this.val != 16) {
            for (int i = 0; i < 3; i++) {
                this.addChild(this.val + moves[i]);
                this.children.get(i).generateTree();
            }
        }
    }

    // Piešķir minimax novērtējumus spēles koka beigu virsotnēm, nosakot kurš veic uzvarošo gājienu
    void minimax_setGameEndNodeValues(int turn) {
        if (this.val == 16 || this.val == 14) this.minimaxScore = 0;
        if (this.val > 17) this.minimaxScore = turn;
        if (this.val == 17) this.minimaxScore = -turn;
        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).minimax_setGameEndNodeValues(-turn);
        }
    }

    // Rekursīvi piešķir minimax novērtējumus visām spēles koka virsotnēm.
    //Lai šo izdarītu sākumā nepieciešams uzzināt gala virsotņu novērtējumus ko piešķir minimax_setGameEndNodeValues();
    int minimax_evaluateAllNodes(int turn) {
        if (this.children.size() == 0) return this.minimaxScore;
        
        //Nosaka virsotnes minimax novērtējumu, izmantojot tā pēcteču novērtējumus un attiecīgi tam vai šis gājiens pieder min vai max spēlētājam.

        int minimax = 111;
        for (int i = 0; i < this.children.size(); i++) {
            int childEval = this.children.get(i).minimax_evaluateAllNodes(-turn);
            if (minimax == 111) { minimax = childEval; continue; }
            if (turn < 0) {
                if (childEval > minimax) minimax = childEval;
            }
            else if (turn > 0) {
                if (childEval < minimax) minimax = childEval;
            }
        }
        this.minimaxScore = minimax;
        return minimax;
    }

    // Print metode kas izvada speles koku ar katras virsotnes minimax novērtējumiem.
     void outputChildren() {
        System.out.print(this.val + "(" + this.minimaxScore + ") ");
        if (this.children.size() == 0) { System.out.println(); return; }
        for (int i = 0; i < this.children.size(); i++) {
            System.out.print(this.children.get(i).val + "(" + this.children.get(i).minimaxScore + ") ");
        }
        System.out.println();

        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).outputChildren();
        }
    }
   
}


