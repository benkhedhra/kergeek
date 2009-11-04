package ihm;

public class Inputbox extends javax.swing.JDialog {
        /** A return status code - returned if Cancel button has been pressed */
        public static final int RET_CANCEL = 0;
        /** A return status code - returned if OK button has been pressed */
        public static final int RET_OK = 1;

        /** Creates new form InputBox */
        public Inputbox(java.awt.Frame parent, boolean modal, String strMessage) {
                super(parent, modal);
                InitInputBox(strMessage);
        }

        /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
        public int getReturnStatus() {
                return returnStatus;
        }
        public String getReponse(){
                return this.txtReponse.getText().trim();
        }

        private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
                doClose(RET_OK);
        }

        private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
                doClose(RET_CANCEL);
        }

        /** Closes the dialog */
        private void closeDialog(java.awt.event.WindowEvent evt) {
                doClose(RET_CANCEL);
        }

        private void doClose(int retStatus) {
                returnStatus = retStatus;
                setVisible(false);
                dispose();
        }

        /* Ma propre méthode pour initialiser la boite de dialogue.
         * En fait je n'ai fait que copier l'initComponent() que l'assistant a créé et
         * j'insère mon propre texte qui est passé en paramètre.*/
        public void InitInputBox(String strMessage){
                buttonPanel = new javax.swing.JPanel();
                okButton = new javax.swing.JButton();
                cancelButton = new javax.swing.JButton();
                textBoxPanel = new javax.swing.JPanel();
                lblQuestion = new javax.swing.JLabel();
                txtReponse = new javax.swing.JTextField();

                addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent evt) {
                                closeDialog(evt);
                        }
                });

                buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

                okButton.setText("OK");
                okButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                okButtonActionPerformed(evt);
                        }
                });

                buttonPanel.add(okButton);

                cancelButton.setText("Cancel");
                cancelButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cancelButtonActionPerformed(evt);
                        }
                });

                buttonPanel.add(cancelButton);

                getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

                lblQuestion.setText(strMessage);
                textBoxPanel.add(lblQuestion);

                txtReponse.setText(" ");
                textBoxPanel.add(txtReponse);

                getContentPane().add(textBoxPanel, java.awt.BorderLayout.CENTER);

                pack();
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel buttonPanel;
        private javax.swing.JButton cancelButton;
        private javax.swing.JLabel lblQuestion;
        private javax.swing.JButton okButton;
        private javax.swing.JPanel textBoxPanel;
        private javax.swing.JTextField txtReponse;
        // End of variables declaration//GEN-END:variables

        private int returnStatus = RET_CANCEL;
}