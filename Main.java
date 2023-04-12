import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class Main {
    // Definēju visu kas ir nepieciešams priekš grafiskā interfeisa un spēles funkcionalitātes
     static JFrame frame;
     static JLabel currentValue,winnerLabel,computerMove;
     static JCheckBox playerCheckBox, computerCheckBox;
     static JButton addTwo, addFive, addSeven, startGame;
     static Game spele;
     static boolean computerTurn;
     static int playerFirst;
     static int beforeBot, afterBot; // Glabā datora gājienu stāvokļus kas ir nepieciešami, lai izvadītu kādu gājienu veica dators.
    public static void main(String[] args) {
        // Izveidoju visus grafiskā interfeisa objektus
        frame = new JFrame("Spele 17");
        playerCheckBox = new JCheckBox("Speletajs");
        computerCheckBox = new JCheckBox("Dators");
        addTwo = new JButton("+2");
        addFive = new JButton("+5");
        addSeven = new JButton("+7");
        startGame = new JButton("Start Game");
        currentValue = new JLabel("Tagadejais skaitlis: ");
        winnerLabel = new JLabel("Uzvaretajs: ");
        computerMove = new JLabel("Dators izvelejas: ");

        //Sadalu pa paneļiem lai elementi grupētos un varētu piešķirt pārskatāmāku izkārtojumu
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(playerCheckBox);
        checkBoxPanel.add(computerCheckBox);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(addTwo);
        buttonPanel.add(addFive);
        buttonPanel.add(addSeven);

        JPanel labelPanel = new JPanel(new GridLayout(3, 1));
        labelPanel.add(computerMove);
        labelPanel.add(currentValue);
        labelPanel.add(winnerLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(labelPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startGame);

        // Galvenais panelis ko pievienot interfeisa logam.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(checkBoxPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Pievienojam visus elementus interfeisa logam
        frame.add(mainPanel); 
        frame.setTitle("Spele 17");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        frame.setVisible(true);

        //Izveidoju Action listeneru lai piešķirtu grafiskā interfeisa pogām funkcionalitāti.
        ActionListener action = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==startGame){  // Pārbauda vai ir nospiesta Start game poga kas uzsāk spēli.
                    beforeBot =0;
                    afterBot=0;
                    if(playerCheckBox.isSelected()){ //Pārbauda un iestata visas vajadzīgās vērtības ja atzīmēts ka spelētājs sāk pirmais.
                        spele = new Game();
                        computerTurn = false;
                        playerFirst = -1;
                        spele.computerFirst(false);
                        computerCheckBox.setSelected(false);
                        computerMove.setText("Dators izvelejas: " );
                        currentValue.setText("Tagadejais skaitlis: ");
                    }else if(computerCheckBox.isSelected()){ // Pārbauda un iestata visas vajadzīgās vērtības ja atzīmēts ka dators sāk pirmais.
                        spele = new Game();
                        computerTurn = true;
                        playerFirst=1;
                        spele.computerFirst(true);
                        playerCheckBox.setSelected(false);
                        beforeBot = spele.getCurrentGameState();
                        spele.MoveByMinimaxEvaluation();
                        afterBot = spele.getCurrentGameState();
                        computerMove.setText("Dators izvelejas: "+String.valueOf(afterBot-beforeBot));
                        currentValue.setText("Tagadejais skaitlis: ");
                    }
                    updateLabels();
                    winnerLabel.setText("Uzvaretajs: ");
                    //Pārbauda vai tiek nospiesta spēles gājiena poga un izdara gājienus.
                }else if(e.getSource()==addTwo){
                    spele.makeMove(1);
                    updateLabels();
                    beforeBot = spele.getCurrentGameState();
                    spele.MoveByMinimaxEvaluation();
                    afterBot = spele.getCurrentGameState();
                    updateLabels();
                }else if(e.getSource()==addFive){
                    spele.makeMove(2);
                    updateLabels();
                    beforeBot = spele.getCurrentGameState();
                    spele.MoveByMinimaxEvaluation();
                    afterBot = spele.getCurrentGameState();
                    updateLabels();
                }else if(e.getSource()==addSeven){
                    spele.makeMove(3);
                    updateLabels();
                    beforeBot = spele.getCurrentGameState();
                    spele.MoveByMinimaxEvaluation();
                    afterBot = spele.getCurrentGameState();
                    updateLabels();
                }
            }
            // Metode kas spēles laikā atjaunos informāciju par rezultātu, un veiktajiem gājieniem.
            private void updateLabels(){
                if(spele!=null){
                    if(spele.getCurrentGameState()>2){
                    computerMove.setText("Dators izvelejas: " + String.valueOf(afterBot-beforeBot));
                    }else{
                        computerMove.setText("Dators izvelejas: " + String.valueOf(afterBot));
                    }
                    currentValue.setText("Tagadejais skaitlis: "+spele.getCurrentGameState());
                    if(!spele.gameActive()){
                        String winner ="";
                        if(spele.getWinner()==0){
                            winner = "Neizskirts";
                        }else if(spele.getWinner()==playerFirst){
                            winner = "Speletajs";
                        }else{
                            winner = "Dators";
                        }
                        winnerLabel.setText("Uzvaretajs: " + winner);
                    }
                }
            }
        };
    //Piešķiram visām pogām un checkboxiem notikumus
    startGame.addActionListener(action);
    playerCheckBox.addActionListener(action);
    computerCheckBox.addActionListener(action);
    addTwo.addActionListener(action);
    addFive.addActionListener(action);
    addSeven.addActionListener(action);

    }
}