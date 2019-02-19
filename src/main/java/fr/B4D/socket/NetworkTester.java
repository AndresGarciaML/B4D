package fr.B4D.socket;

import java.util.concurrent.CountDownLatch;

import net.sourceforge.jpcap.capture.CaptureDeviceOpenException;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.InvalidFilterException;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.RawPacketListener;
import net.sourceforge.jpcap.net.RawPacket;

/** La classe {@code NetworkTester} �tend la classe  {@code Thread}.
 * Elle permet de tester si un r�seau est actif.
 * Un testeur de r�seau est identifi� par un nom de r�seau et un nombre de packet minimum.
 */
public class NetworkTester extends Thread{

	  /**************/
	 /** ATRIBUTS **/
	/**************/
	
	private PacketCapture m_pcap;
	private CountDownLatch socketDetected;
	
	private boolean actif;
	private String network;

	  /*************/
	 /** BUILDER **/
	/*************/
	
	/** Constructeur de la classe {@code NetworkTester}. 
	 * @param m_device - Nom du r�seau � tester.
	 * @param socketDetected - Nombre de trames � d�tecter pour consid�rer le r�seau comme actif.
	 */
	public NetworkTester(String network, CountDownLatch socketDetected) {
		this.m_pcap = new PacketCapture();
		this.actif = false;
		this.network = network;
		this.socketDetected = socketDetected;
	}

	  /*************/
	 /** GETTERS **/
	/*************/
	
	/** Retourne le nom du r�seau.
	 * @return Nom du r�seau.
	 */
	public String getNetwork() {
		return network;
	}
	
	/** Sp�cifi si le r�seau est actif.
	 * @return {@code true} si le r�seau est actif, {@code false} sinon.
	 */
	public boolean isActif() {
		return actif;
	}

	  /*********/
	 /** RUN **/
	/*********/
	
	public void run() {
		try {
			m_pcap.open(network, true);
			m_pcap.setFilter("", true);
			m_pcap.addRawPacketListener(new RawPacketHandler());
			m_pcap.capture(1);
		} catch (CaptureDeviceOpenException | InvalidFilterException | CapturePacketException e) {}
	}

	class RawPacketHandler implements RawPacketListener 
	{
		public void rawPacketArrived(RawPacket data) {
			actif = true;
			socketDetected.countDown();
		}
	}
}
