package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ExchangeDialog extends JFrame implements ActionListener {
    List<Cube> currenciesAndRatios = new CurrenciesAndRatiosGenerator().generateCurrenciesAndRatios();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExchangeDialog("EUR to x coverter"));
    }

    public ExchangeDialog(String title) throws HeadlessException {
        super(title);
        prepareJframe();
        this.getContentPane().add(new JLabel("<html>Wprowadź kod waluty<br/> na którą chcesz dokonać<br/> konwersji</html>"));
        JComboBox<String> currenciesCombobox = prepareCombobox(currenciesAndRatios);
        this.getContentPane().add(currenciesCombobox);
        this.getContentPane().add(new JLabel("Podaj ilość pieniędzy w Euro"));
        JTextField amountTextField = prepareAmountTf();
        this.getContentPane().add(amountTextField);
        JLabel summary = new JLabel("");
        this.getContentPane().add(summary);
        this.getContentPane().add(prepareCalculateButton(currenciesCombobox,amountTextField,summary));

    }

    private JTextField prepareAmountTf() {
        JTextField jTextField = new JTextField();

        return jTextField;
    }

    private JButton prepareCalculateButton(JComboBox<String> currenciesCombobox, JTextField ammountTextField, JLabel summary) {
        JButton calculateButton = new JButton("Przelicz");
        calculateButton.addActionListener(evt -> {
            int selectedIndex = currenciesCombobox.getSelectedIndex();
            Cube cube = currenciesAndRatios.get(selectedIndex);
            String sum = calculateAmount(ammountTextField.getText(), cube.getRate());
            summary.setText(ammountTextField.getText() + " EUR to " + cube.getCurrency() + " = " + sum);
        });

        return calculateButton;
    }

    private String calculateAmount(String amount, double rate) {
        return BigDecimal.valueOf(Double.parseDouble(amount)).multiply(BigDecimal.valueOf(rate)).setScale(2, RoundingMode.CEILING).toString();
    }

    private JComboBox<String> prepareCombobox(List<Cube> currenciesAndRatios) {
        String[] currencies = currenciesAndRatios.stream().map(Cube::getCurrency).toArray(String[]::new);

        JComboBox<String> currenciesList = new JComboBox<>(currencies);
        currenciesList.setSelectedIndex(0);
        currenciesList.addActionListener(this);
        return currenciesList;
    }

    private void prepareJframe() {
        this.setSize(250, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
