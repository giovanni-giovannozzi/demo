package giovanni.demo;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import giovanni.demo.parser.CheckResult;
import giovanni.demo.parser.Status;

public class VisualChecker {
	public static void check() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("Number", SwingConstants.RIGHT));
		panel.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		JTextField number = new JTextField();
		controls.add(number);
		panel.add(controls, BorderLayout.CENTER);

		while (true) {
			int ret = JOptionPane.showConfirmDialog(null, panel, "login", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION) {
				break;
			}
			CheckResult cr = CheckResult.checkNumber(number.getText());

			Status status = cr.getStatus();
			String message = "Number is " + status.toString();
			if (status == Status.CORRECTED) {
				message = message + " - " + cr.getNumber() + " - " + cr.getCorrection();
			}

			JOptionPane.showMessageDialog(new JFrame(), message, "Check result", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
