import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
//Импортируем необходимые классы из пакетов javax.swing и java.awt, а также классы для работы с событиями и файлами.
public class TextEditor extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    //Определяем класс TextEditor, который наследуется от JFrame. Создаем приватные переменные textArea для текстового поля и fileChooser для выбора файлов.

    public TextEditor() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Конструктор класса TextEditor. Устанавливаем заголовок окна, размеры и операцию закрытия по умолчанию.

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        //Создаем экземпляр JTextArea и оборачиваем его в JScrollPane для добавления полос прокрутки. Затем добавляем scrollPane в центр окна.

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        //Создаем JMenuBar, JMenu с названием "File" и два JMenuItem с названиями "Open" и "Save".

        fileChooser = new JFileChooser();
        //Создаем экземпляр JFileChooser для выбора файлов.

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(TextEditor.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    openFile(file);
                }
            }
        });
        //Добавляем слушатель событий к openMenuItem. При нажатии на него открывается диалоговое окно выбора файла. Если файл выбран, вызывается метод openFile() для открытия файла.

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showSaveDialog(TextEditor.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    saveFile(file);
                }
            }
        });
        //Добавляем слушатель событий к saveMenuItem. При нажатии на него открывается диалоговое окно сохранения файла. Если файл выбран, вызывается метод saveFile() для сохранения файла.

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        //Добавляем openMenuItem и saveMenuItem в fileMenu, а затем fileMenu в menuBar. Устанавливаем menuBar в качестве меню-панели окна.
    }

    private void openFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            textArea.setText(sb.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Метод openFile(), который открывает выбранный файл и читает его содержимое. Содержимое файла добавляется в StringBuilder, а затем устанавливается в текстовое поле textArea. Если происходит ошибка, выводится диалоговое окно с сообщением об ошибке.

    private void saveFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Метод saveFile(), который сохраняет содержимое текстового поля textArea в выбранный файл. Если происходит ошибка, выводится диалоговое окно с сообщением об ошибке.

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TextEditor textEditor = new TextEditor();
                textEditor.setVisible(true);
            }
        });
    }
    //Метод main(), который запускает приложение, создает экземпляр TextEditor и делает его видимым.
}
