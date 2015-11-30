package ldap.login;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUser;

    public Login() {
        this.initComponents();
    }

    private void login() {

        String user = this.txtUser.getText();
		String userDomain = "DEV\\";
        char[] password = this.txtPassword.getPassword();
        final String url = "ldap://192.168.24.17"; //IP ou DSN do AD
        final Properties env = new Properties();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_PRINCIPAL, userDomain + user);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {

            InitialLdapContext ctx = new InitialLdapContext(env, null);
            SearchControls srchControls = new SearchControls();
            srchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			//Verifica se o usuário pertence à pasta "OU=security,OU=intranet,DC=dev,DC=com" e se ele é membro do grupo "CN=ESPECIFIC_GROUP,OU=security,OU=intranet,DC=dev,DC=com"
            NamingEnumeration<SearchResult> result =  ctx.search("OU=security,OU=intranet,DC=dev,DC=com","(&(objectClass=*)(sAMAccountName=" + user + ")(memberof=CN=ESPECIFIC_GROUP,OU=security,OU=intranet,DC=dev,DC=com))", srchControls);

            if (result.hasMore()) {
                JOptionPane.showMessageDialog(null, "Usuário encontrado");
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado");
            }

        } catch (NamingException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar usuário");
            e.printStackTrace();
        }
    }

    private void initComponents() {

        this.jLabel1 = new javax.swing.JLabel();
        this.jLabel2 = new javax.swing.JLabel();
        this.txtUser = new javax.swing.JTextField();
        this.txtPassword = new javax.swing.JPasswordField();
        this.btnOk = new javax.swing.JButton();

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.jLabel1.setText("Usuário:");

        this.jLabel2.setText("Senha:");

        this.btnOk.setText("OK");
        this.btnOk.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login.this.btnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(this.jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addComponent(this.jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(this.txtUser)
                            .addComponent(this.txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(this.btnOk)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(this.jLabel1)
                    .addComponent(this.txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(this.jLabel2)
                    .addComponent(this.txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(this.btnOk)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        this.setSize(new java.awt.Dimension(412, 217));
        this.setLocationRelativeTo(null);
    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
        this.login();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

}