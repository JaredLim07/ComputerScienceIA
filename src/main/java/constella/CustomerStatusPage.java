package constella;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.HashMap;

public class CustomerStatusPage extends JPanel {
    MainWindow parent;
    HashMap<String, Object> companyInfo;
    String[] statusOptions = {"Select", "Satisfied", "Ok", "Dissatisfied"};
    JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
    JSpinner startDateSpinner = new JSpinner();
    JSpinner endDateSpinner = new JSpinner();
    JTextArea commentArea = new JTextArea(10,20);

    public CustomerStatusPage(MainWindow parent,DB db, String companyName){
        super();
        this.parent = parent;
        companyInfo = db.getCompanyInfo(companyName);

        System.out.println("Company info =" + companyInfo);
        init(db, companyName);
    }

    public void init(DB db, String companyName) {
        setBackground(new Color(222, 222, 222));
        setBounds(0, 40, 480, 1200);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(addStatusComboBox());

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout());

        datePanel.add(addStartDatePicker(), BorderLayout.NORTH);
        datePanel.add(addEndDatePicker(), BorderLayout.CENTER);

        topPanel.add(datePanel);

        topPanel.add(addCommentTextArea());


        add(topPanel, BorderLayout.CENTER);

        topPanel.add(saveButton(db, companyName));

        topPanel.add(backButton());



    }

    private JPanel addStatusComboBox() {
        JPanel statusPanel = new JPanel();
        JLabel statusLabel = new JLabel("Status");
        statusPanel.add(statusLabel);

        statusPanel.add(statusComboBox);

        String status = (String) companyInfo.get("status");
        if (!status.isEmpty()) {
            statusComboBox.setSelectedItem(status);
        }else{
            statusComboBox.setSelectedItem("Select");
        }
        return statusPanel;
    }
    private JPanel addStartDatePicker() {
        JPanel startDatePanel = new JPanel();
        JLabel startDateLabel = new JLabel("Start Date:");
        startDatePanel.add(startDateLabel);


        SpinnerDateModel dateModel = new SpinnerDateModel();
        startDateSpinner = new JSpinner(dateModel);
        // startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
        // startDatePanel.add(startDateSpinner);

        Date startDate = (Date) companyInfo.get("startDate");

        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
        startDateSpinner.setValue(startDate);

        startDatePanel.add(startDateSpinner);
        return startDatePanel;
    }
    private JPanel addEndDatePicker() {
        JPanel endDatePanel = new JPanel();
        JLabel endDateLabel = new JLabel("End Date:");
        endDatePanel.add(endDateLabel);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        endDateSpinner = new JSpinner(dateModel);

        Date endDate = (Date) companyInfo.get("endDate");

        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));
        endDateSpinner.setValue(endDate);

        endDatePanel.add(endDateSpinner);

        return endDatePanel;
    }
    private JPanel addCommentTextArea() {
        JPanel commentPanel = new JPanel();
        JLabel commentLabel = new JLabel("Comment");
        commentPanel.add(commentLabel);

        //JTextArea commentArea = new JTextArea(10,20);
        JScrollPane commentScroll = new JScrollPane(commentArea);

        String comment = (String) companyInfo.get("comment");

        commentArea.setText(comment);

        commentPanel.add(commentScroll);

        return commentPanel;
    }
    private JPanel saveButton(final DB db, final String companyName){
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        buttonPanel.add(saveButton);

        //create action listener for save button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String status = (String)statusComboBox.getSelectedItem();

                // Get the start date
                //JPanel startDatePanel = addStartDatePicker();
                var startDate = (java.util.Date)startDateSpinner.getValue();

                // Get the end date
                // JPanel endDatePanel = addEndDatePicker();
                var endDate = (java.util.Date)endDateSpinner.getValue();

                // Get the comment
                // JPanel commentPanel = addCommentTextArea();
                //JScrollPane commentScroll = (JScrollPane)commentPanel.getComponent(1);

                // JTextArea commentArea = (JTextArea)commentScroll.getViewport().getView();
                String comment = commentArea.getText();

                Date sqlStartDate = new Date(startDate.getTime());
                Date sqlEndDate = new Date(endDate.getTime());

                // Call the updateCompany method
                db.updateCompany(companyName, status, comment, sqlStartDate, sqlEndDate);
            }
        });
        return buttonPanel;
    }

    private JButton backButton(){
        JButton back = new JButton("back to home");
        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.backToHome();
                //companyList.getSelectedItem();
                //parent.addPanel();
            }
        });

        return back;
    }
}
