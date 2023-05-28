//package Second;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Main extends JFrame {

    private JLabel labelN;
    private JTextField inputN;
    private JButton buttonCalculate, buttonReadFromFile;
    private JTable stockTable, resultTable;
    private JScrollPane scrollPaneStock, scrollPaneResult;

    Main() {
        super("Lab6");
        labelN = new JLabel("N: ");
        inputN = new JTextField(10);
        buttonCalculate = new JButton("Calculate");
        buttonCalculate.addActionListener(e -> {
            try {
                int N = Integer.parseInt(inputN.getText());
                if (N < 0 || N >= 15) {
                    throw new InvalidNException("N must be between 1 and 15.");
                }
                Object[][] A = new Object[N][N];
                Object[][] L = new Object[1][N];

                for (int i = 0; i < N; i++) {
                    int pos = 0, neg = 0;
                    for (int j = 0; j < N; j++) {
                        Random random = new Random();
                        A[i][j] = random.nextInt(21) - 10; // range [-10, 10]
                        if ((int) A[i][j] > 0) pos++;
                        else if ((int) A[i][j] < 0) neg++;
                    }
                    if (neg > pos) {
                        L[0][i] = true;
                    } else {
                        L[0][i] = false;
                    }
                }
                String[] columnNames = new String[N];
                for (int i = 0; i < N; i++) {
                    columnNames[i] = Integer.toString(i + 1);
                }

                stockTable.setModel(new javax.swing.table.DefaultTableModel(A, columnNames));
                resultTable.setModel(new javax.swing.table.DefaultTableModel(L, columnNames));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Main.super.getContentPane(), "Please enter a valid integer for N.");
            } catch (InvalidNException ex) {
                JOptionPane.showMessageDialog(Main.super.getContentPane(), ex.getMessage());

            }

        });

        buttonReadFromFile = new JButton("Read from file");
        buttonReadFromFile.addActionListener(e -> {
            try {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    // read matrix from file
                    File file = chooser.getSelectedFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    int N = Integer.parseInt(reader.readLine());
                    if (N < 0 || N >= 15) {
                        throw new InvalidNException("N must be between 1 and 15.");
                    }
                    Object[][] A = new Object[N][N];
                    Object[][] L = new Object[1][N];
                    for (int i = 0; i < N; i++) {
                        int neg = 0, pos = 0;
                        String[] line = reader.readLine().split("\\s+");
                        for (int j = 0; j < N; j++) {
                            A[i][j] = Integer.parseInt(line[j]);
                            if ((int) A[i][j] > 0) pos++;
                            else if ((int) A[i][j] < 0) neg++;
                        }
                        if (neg > pos) {
                            L[0][i] = true;
                        } else {
                            L[0][i] = false;
                        }
                    }
                    reader.close();
                    String[] columnNames = new String[N];
                    for (int i = 0; i < N; i++) {
                        columnNames[i] = Integer.toString(i + 1);
                    }
                    inputN.setText(Integer.toString(N));
                    stockTable.setModel(new javax.swing.table.DefaultTableModel(A, columnNames));
                    resultTable.setModel(new javax.swing.table.DefaultTableModel(L, columnNames));


                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Main.super.getContentPane(), "Can't open file.");
            } catch (InvalidNException ex) {
                JOptionPane.showMessageDialog(Main.super.getContentPane(), ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Main.super.getContentPane(), "Can't read file properly.");
            }
        });
        stockTable = new JTable();
        resultTable = new JTable();
        scrollPaneStock = new JScrollPane(stockTable);
        scrollPaneStock.setPreferredSize(new Dimension(400, 200));
        scrollPaneResult = new JScrollPane(resultTable);
        scrollPaneResult.setPreferredSize(new Dimension(400, 200));
        JPanel panel = new JPanel();
        panel.add(labelN);
        panel.add(inputN);
        panel.add(buttonCalculate);
        panel.add(buttonReadFromFile);
        panel.add(scrollPaneStock);
        panel.add(scrollPaneResult);

        getContentPane().add(panel);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    public static void main(String[] args) {
        Main app = new Main();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
