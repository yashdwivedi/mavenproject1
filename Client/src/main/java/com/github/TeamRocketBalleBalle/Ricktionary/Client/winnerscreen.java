package com.github.TeamRocketBalleBalle.Ricktionary.Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author tiwar
 */
public class winnerscreen extends javax.swing.JPanel {

    /**
     * Creates new form winnerscreen
     */
    public winnerscreen() {
        initComponents();
    }
    //     public static void moosic(String []args){
    //
    //        String soundName = "yourSound.wav";
    //         AudioInputStream audioInputStream = null;
    //         try {
    //             audioInputStream = AudioSystem.getAudioInputStream(new
    // File("Client/src/main/resources/ph_intro.mp3").getAbsoluteFile());
    //         } catch (UnsupportedAudioFileException e) {
    //             e.printStackTrace();
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //         Clip clip = null;
    //         try {
    //             clip = AudioSystem.getClip();
    //         } catch (LineUnavailableException e) {
    //             e.printStackTrace();
    //         }
    //         try {
    //             clip.open(audioInputStream);
    //         } catch (LineUnavailableException e) {
    //             e.printStackTrace();
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //         clip.start();
    //    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        ImageIcon icon = new ImageIcon("Client/src/main/resources/triggeredjantu.gif");
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(1077, 767, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(imgscale);

        back = new javax.swing.JLabel();
        winnergif = new javax.swing.JLabel();
        JLabel jLabel3 = new JLabel();
        back.setIcon(scaledIcon);
        // setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1077, 767));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addGap(450, 450, 450)
                                        .addComponent(
                                                jLabel3,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                250,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addGap(395, 395, 395)
                                        .addComponent(
                                                winnergif,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                301,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
//                        .addComponent(
//                                back,
//                                javax.swing.GroupLayout.PREFERRED_SIZE,
//                                1080,
//                                javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(
                                                jLabel3,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                100,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addGap(773, 773, 773)
                                        .addComponent(
                                                winnergif,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                154,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
//                        .addComponent(
//                                back,
//                                javax.swing.GroupLayout.PREFERRED_SIZE,
//                                1010,
//                                javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        back.setBounds(new Rectangle(1077, 767));
        add(back);
        // pack();
    } // </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(winnerscreen.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new winnerscreen().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel back;
    private javax.swing.JLabel winnergif;
    // End of variables declaration
}
