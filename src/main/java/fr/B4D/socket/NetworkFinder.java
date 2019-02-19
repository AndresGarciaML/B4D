package fr.B4D.socket;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import fr.B4D.bot.B4DException;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.PacketCapture;

/** La classe {@code NetworkFinder} repr�sente un rechercheur de r�seau.
 */
public class NetworkFinder{

	private final static int millisToWait = 3000;

	/** Permet de r�cup�rer le r�seau actuellement utilis�.
	 * @return Nom du r�seau utilis�.
	 * @throws B4DException Si aucuns des r�seaux n'est actif.
	 * @throws CaptureDeviceLookupException Si aucun r�seau n'est d�tect�.
	 */
	public static String find() throws CaptureDeviceLookupException, B4DException {
		String[] devs = PacketCapture.lookupDevices();
		ArrayList<NetworkTester> testers = new ArrayList<NetworkTester>();
		CountDownLatch socketDetected = new CountDownLatch(1);

		for(int i=0; i<devs.length; i++) {
			devs[i] = devs[i].substring(0, devs[i].indexOf("\n"));
			testers.add(new NetworkTester(devs[i], socketDetected));
		}
		testers.stream().forEach(s -> s.start());
		try {
			socketDetected.await(millisToWait, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testers.stream().forEach(s -> s.interrupt());
		
		for(NetworkTester tester:testers) {
			if(tester.isActif())
				return tester.getNetwork();
		}
		throw new B4DException("Couldn't find any packet on any device. Please try again.");
	}
}
