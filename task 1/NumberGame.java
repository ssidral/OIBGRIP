import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.transform.OutputKeys;

public class NumberGame {
  int RandomNumber = 0;
  int Answer = 0;
  int count = 10;

  public static void main(String[] args) {
    NumberGame nb = new NumberGame();
    JFrame f = new JFrame();
    f.setTitle("Number Guessing Game");

    JLabel Heading = new JLabel("..NUMBER GUESSING GAME..");
    Heading.setSize(700, 40);
    Heading.setFont(new Font("Serif", Font.BOLD, 30));
    Heading.setHorizontalAlignment(JTextField.CENTER);

    JLabel Text_line = new JLabel(
      "<html>Try & Guess a random number between 1 and 100 <br> You have 10 attempts to guess the right number...!</html>"
    );
    Text_line.setBounds(0, 50, 700, 60);
    Text_line.setFont(new Font("Serif", Font.PLAIN, 20));
    Text_line.setHorizontalAlignment(JLabel.CENTER);

    JLabel l1 = new JLabel();
    l1.setIcon(new ImageIcon("question_mark.png"));
    l1.setBounds(-40, 250, 360, 400);

    JPanel panel = new JPanel();
    panel.setBounds(330, 200, 300, 400);
    panel.setBackground(Color.LIGHT_GRAY);

    JLabel panelHeading = new JLabel("Guess a number");
    panelHeading.setSize(300, 40);
    panelHeading.setFont(new Font("Serif", Font.BOLD, 30));
    panelHeading.setHorizontalAlignment(JLabel.CENTER);

    JTextField userInput = new JTextField();
    userInput.setBounds(100, 50, 100, 50);
    userInput.setFont(new Font("Serif", Font.BOLD, 40));
    userInput.setHorizontalAlignment(JTextField.CENTER);

    JButton submit_button = new JButton("Submit Guess");
    submit_button.setBounds(20, 130, 120, 30);

    JButton quite_button = new JButton("Exit");
    quite_button.setBounds(160, 130, 120, 30);

    JLabel Guess_count = new JLabel("Guesses Remianing : 10");
    Guess_count.setBounds(20, 170, 250, 30);
    Guess_count.setFont(new Font("Serif", Font.BOLD, 20));
    Guess_count.setHorizontalAlignment(JLabel.CENTER);

    JLabel Score_count = new JLabel("Your Score : 0 ");
    Score_count.setBounds(20, 210, 250, 30);
    Score_count.setFont(new Font("Serif", Font.BOLD, 20));
    Score_count.setHorizontalAlignment(JLabel.CENTER);

    JLabel Margin_count = new JLabel();
    Margin_count.setBounds(10, 250, 290, 30);
    Margin_count.setFont(new Font("Serif", Font.BOLD, 20));
    Margin_count.setHorizontalAlignment(JLabel.CENTER);
    Margin_count.setForeground(Color.RED);

    JLabel result = new JLabel();
    result.setBounds(20, 320, 270, 50);
    result.setFont(new Font("SansSerif", Font.BOLD, 20));
    result.setHorizontalAlignment(JLabel.CENTER);
    result.setForeground(Color.RED);

    panel.add(panelHeading);
    panel.add(userInput);
    panel.add(submit_button);
    panel.add(quite_button);
    panel.add(Guess_count);
    panel.add(Score_count);
    panel.add(Margin_count);
    panel.add(result);
    panel.setLayout(null);
    f.add(panel);
    f.add(Heading);
    f.add(Text_line);
    f.add(l1);

    //f.add(b);

    f.setLayout(null);
    f.setSize(700, 700);
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    f.setVisible(true);
    quite_button.addActionListener(
      new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
          f.dispose();
        }
      }
    );
    submit_button.addActionListener(
      new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
          nb.RandomNumber = (int) (Math.random() * 100 + 1);
          String response = userInput.getText();

          System.out.println(nb.RandomNumber);
          if (response.isEmpty()) {
            Margin_count.setText("Enter Number Please..");
          } else {
            nb.Answer = Integer.parseInt(response);
            result.setText("");
            Score_count.setText("Your Score : 0");
            Margin_count.setText("");
            if (nb.count == 1) {
              userInput.setText("");

              result.setText("Better Luck Next Time!");
              result.setForeground(Color.RED);
              JOptionPane.showMessageDialog(
                null,
                "Game Over...!",
                "Guessing Game",
                2
              );

              nb.count = 10;
              Guess_count.setText("Guesses Remianing : " + nb.count);
              result.setText("");
            } else {
              userInput.setText("");

              nb.count--;
              Guess_count.setText("Guesses Remianing : " + nb.count);
            }
            if (nb.Answer <= 0 || nb.Answer > 100 || response == "") {
              Margin_count.setText("Your guess is invalid");
            } else if (nb.Answer == nb.RandomNumber) {
              double score = nb.count * 1000;
              Score_count.setText("Your Score : " + score);
              result.setText("WINNER ");
              result.setForeground(Color.GREEN);
              Margin_count.setText("Your guess is correct");
            } else if (nb.Answer > nb.RandomNumber) {
              Margin_count.setText("Your guess is too high, try again. ");
            } else if (nb.Answer < nb.RandomNumber) {
              Margin_count.setText("Your guess is too low, try again.");
            } else {
              result.setText("Your guess is incorrect\nTry Number: ");
            }
          }
        }
      }
    );
  }
}
