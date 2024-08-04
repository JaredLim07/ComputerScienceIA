package constella;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;

public class HomePage extends JPanel {
    MainWindow parent;
    JComboBox<String> companyList;
    static DB myDB;

    public HomePage(MainWindow parent){
        super();
        this.parent = parent;
        init();
    }

    public void init(){
        myDB = new DB();

        JPanel chartPanel = new XChartPanel<PieChart>(getPieChart());
        add(chartPanel);
        add(dropdown());
        add(goButton());
        add(addCustomerButton());

        setBackground(new Color(222, 222, 222));
        setBounds(0, 0, 480, 800);
    }

    private PieChart getPieChart() {
        PieChart chart = new PieChartBuilder().width(480).height(400).title("Sample").build();

        chart.getStyler().setCircular(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);

        chart.getStyler().setSeriesColors(new Color[]{
                        Color.GREEN,
                        Color.YELLOW,
                        Color.RED
                });

        chart.addSeries("Satisfied", myDB.satisfiedCompanies());
        chart.addSeries("Ok", myDB.okCompanies());
        chart.addSeries("Dissatisfied", myDB.dissatisfiedCompanies());

        return chart;

    }
    public void addCompaniesToDropdown(){
        var companies = myDB.getCompanyName();
        String[] companyArray = (String[]) companies.toArray(new String[0]);
        companyList.removeAllItems();
        // Loop through companies
        for (String s : companyArray) {
            companyList.addItem(s);
        }
        companyList.revalidate();
        companyList.repaint();
    }
    private JComboBox<String> dropdown(){

        var companies = myDB.getCompanyName();
        String[] companyArray = (String[]) companies.toArray(new String[0]);
        companyList = new JComboBox<String>(companyArray);
        if (companyArray.length > 0)
            companyList.setSelectedIndex(0);

        return companyList;
    }

    private JButton goButton(){
        JButton go = new JButton("GO");
        String selectedCompany = (String) companyList.getSelectedItem();
        go.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.removeAndAdd(selectedCompany);
                //companyList.getSelectedItem();
                //parent.addPanel();
            }
        });

        return go;
    }

    private JButton addCustomerButton(){
        JButton add = new JButton("Add Customer");

        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(null, "Enter customer name:");
                if (input != null && !input.isEmpty()) {
                    if (!Arrays.asList(companyList).contains(input)) {
                        myDB.addCompany(input);
                        addCompaniesToDropdown();
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer already exists!");
                    }
                }
            }
        });
        return add;
    }
}