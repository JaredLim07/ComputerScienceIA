package constella;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame = null;
    JPanel mainPanel = null;
    Container curPage =  null;
    Container homePage =  null;

    public MainWindow(){
        SwingUtilities.invokeLater(this::showFrame);
    }

    public static void main(String[] args){
        new MainWindow();

    }

    private void showFrame(){
        frame = new JFrame();
        frame.setTitle("Customer Support");
        frame.setSize(new Dimension(480, 840));

        GridLayout gridLayout = new GridLayout(1, 1);
        frame.setLayout(gridLayout);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = mainPanel();
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public JPanel mainPanel(){
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(10, 20, 30));


        panel.add(titleBar());

        curPage = new HomePage(this);
        panel.add(curPage);

        return panel;
    }

    public JPanel titleBar(){
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(222, 222, 222));
        panel.setBounds(0, 0, 480, 45);

        JLabel title = new JLabel("Customer Success Chart");
        title.setBounds(160, 0, 480, 45);

        panel.add(title);

        return panel;
    }

    public void removePanel(Component page){
        mainPanel.remove(page);
    }
    public void removePanel() {
        mainPanel.remove(curPage);
        mainPanel.invalidate();
        mainPanel.validate();
        mainPanel.repaint();
    }
//    public void addPanel(){
//        mainPanel.add(new CustomerStatusPage());
//        mainPanel.invalidate();
//        mainPanel.validate();
//        mainPanel.repaint();
//    }

    public void removeAndAdd(String companyName){
        homePage = curPage;
        mainPanel.remove(curPage);
        curPage = new CustomerStatusPage(this, HomePage.myDB, companyName);
        mainPanel.add(curPage);

        mainPanel.revalidate();
        mainPanel.repaint();

    }

    public void backToHome(){
        mainPanel.remove(curPage);
        curPage = homePage;
        mainPanel.add(homePage);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
