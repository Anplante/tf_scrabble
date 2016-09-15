package ca.qc.bdeb.p56.scrabble.vue;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

/**
 * Created by Antoine on 9/12/2016.
 */
public class PanelEditGame extends  JFrame {

    private JPanel panelMenu;
    private JTextField txtNom;
    private JButton btnConfirm;
    private JLabel lblNom;
    private JButton btnAnnuler;
    private JLabel lblNombreAI;
    private String[] nombreDeAi = { "1", "2", "3"};
    private JComboBox cmbNombreAi = new JComboBox();

    public PanelEditGame() {

        super();

        setLayout(null);

        JFrame fenetre = new JFrame();
        this.setTitle("Menu");
        fenetre.pack();
        Insets insets = fenetre.getInsets();
        fenetre = null;
        setSize(new Dimension(insets.left + insets.right + 400,
                insets.top + insets.bottom + 400));

       // panelMenu.setLayout(new BorderLayout());


        initializeComponents();



        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);




    }


    private void initializeComponents() {
        panelMenu = new JPanel();
        panelMenu.setLayout(null);
        ajouterTextBox();
        ajouterLesLabels();
        ajouterBoutons();
        ajouterComboBox();

        add(panelMenu);
    }

    private void ajouterComboBox() {
        cmbNombreAi = new JComboBox();
        for (int i = 0; i < nombreDeAi.length; i++) {
            cmbNombreAi.addItem(nombreDeAi[i]);
        }
        cmbNombreAi.setVisible(true);
        cmbNombreAi.setLocation(180, 120);
        cmbNombreAi.setSize(100,25);
        panelMenu.add(cmbNombreAi);
    }
    private void ajouterBoutons() {
        btnAnnuler = new JButton();
        btnConfirm = new JButton();
        btnAnnuler.setVisible(true);
        btnConfirm.setVisible(true);
        btnAnnuler.setSize(100,50);
        btnConfirm.setSize(100,50);
        btnAnnuler.setText("Annuler");
        btnConfirm.setText("Confirmer");
        btnAnnuler.setLocation(250, 325);
        btnConfirm.setLocation(50,325);
        btnConfirm.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("allotest123");
            }
        });
        btnAnnuler.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("test");
            }
        });
        panelMenu.add(btnConfirm);
        panelMenu.add(btnAnnuler);
    }

    private void ajouterLesLabels() {
        lblNom = new JLabel();
        lblNom.setText("Nom du Joueur : ");
        lblNom.setLocation(25,25);
        lblNom.setSize(lblNom.getPreferredSize());
        lblNom.setVisible(true);

        lblNombreAI = new JLabel();
        lblNombreAI.setText("Nombre d'ordinateurs :");
        lblNombreAI.setLocation(25, 125);
        lblNombreAI.setSize(lblNombreAI.getPreferredSize());
        lblNombreAI.setVisible(true);
        panelMenu.add(lblNom);
        panelMenu.add(lblNombreAI);
    }

    private void ajouterTextBox() {
        panelMenu.setLocation(0,0);
        panelMenu.setSize(new Dimension(400, 400));
        txtNom = new JTextField("", 30);
        txtNom.setBounds(150,20,180,30);

        txtNom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = txtNom.getText();
                txtNom.setText(input);
            }
        });
        panelMenu.add(txtNom);
    }
}