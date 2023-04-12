// Gustavs Brizga 211RDB356 10.grupa
public class Game { // Spēles klase, kas satur spēles koku un tās funkcionalitāti.

  Node gameTree = null;
  Node currentState = null;
  boolean gameOngoing = true;

  //Mainīgais kas nosaka, vai dators veic gājienu pirmais
  // -1, dators iet pirmais(minimizētājs)
  // 1, dators iet otrais(maksimizētājs)
  int maximizer = 1;

  //Konstruktors kas sagatavo spēli un minimax novērtējumus
  Game() {
      this.gameTree = new Node(0);
      this.gameTree.generateTree();
      this.gameTree.minimax_setGameEndNodeValues(-1);
      this.gameTree.minimax_evaluateAllNodes(1);
      this.currentState = gameTree;
      this.gameOngoing = true;

      //Lai izvadītu spēles koku konsolē jāizņem nākamā rindiņa no komentāra.
     // this.outputTreeToConsole();
  }
  // Veic gājienu, attiecīgi uzstādot pašreizējo spēles stāvokli uz nepieciešamo virsotni.
  // Pēc katra gajiena pārbauda vai ir nonākts līdz spēles gala stāvoklim.
  public void makeMove(int num) {
      if (num == 1) {
          this.currentState = this.currentState.children.get(0);
      }
      if (num == 2) {
          this.currentState = this.currentState.children.get(1);
      }
      if (num == 3) {
          this.currentState = this.currentState.children.get(2);
      }

      this.checkForEndState();
  }
  //Atgriež pašreizējo spēles stāvokli.
  public int getCurrentGameState() {
      return this.currentState.val;
  }
  // Atgriež pašreizējā spēles stāvokļa minimax novērtējumu
  public int getCurrentMinimaxEval() {
      return this.currentState.minimaxScore;
  }
  // Datora gājiens pēc minimax novērtējuma
  public void MoveByMinimaxEvaluation() {
      int currentEval = this.currentState.minimaxScore;
      //Meklē labāko iespējamo gājienu attiecīgi tam vai dators ir minimizētājs vai maksimizētājs.
      //Piemēram : no stāvokļa 0 uz -1 , ja dators ir minimizētājs, vai no 0 uz 1 ja dators ir maksimizētājs.
      for (int i = 0; i < this.currentState.children.size(); i++) {
          if (-this.maximizer * this.currentState.children.get(i).minimaxScore < -this.maximizer * currentEval) {
              this.makeMove(i + 1);
              return;
          }
      }
      //Ja labāka gājiena nav, tad veic gājienu kas nemainītu spēles gaitu vai iet uz gajienu kas noved pie neizšķirta.
      for (int i = 0; i < this.currentState.children.size(); i++) {
          if (this.currentState.children.get(i).minimaxScore == currentEval) {
              makeMove(i + 1);
              return;
          }
      }
  }
  // Pārbauda vai dators ies pirmais vai otrais un uzstāda attiecīgās vērtības kas der maksimizētājam.
  public void computerFirst(boolean first) {
      if (first) {
          this.maximizer = -1;
      }
      else {
          this.maximizer = 1;
      }
  }
  // Pārbauda, vai spēle ir sasniegusi gala nosacījumus. Ja ir tad izbeidz spēli.
  public void checkForEndState() {
      if (this.currentState.children.size() == 0) {
          this.endGame();
      }
  }
  // Spēles beigšanas funkcija
  public void endGame() {
      this.gameOngoing = false;
  }
  // Pārbauda vai spēle ir procesā.
  public boolean gameActive() {
      if (this.gameOngoing) {
          return true;
      }
      return false;
  }

  // Atgriež spēles uzvarētāju.
  public int getWinner() {
      return this.currentState.minimaxScore;
  }
  // Izsauc spēles koka izvadi uz konsoli.
  
  public void outputTreeToConsole() {
    this.gameTree.outputChildren();
}
}