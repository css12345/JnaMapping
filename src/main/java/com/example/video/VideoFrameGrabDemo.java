package com.example.video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_java;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class VideoFrameGrabDemo {

	public static void main(String[] args) throws IOException {
		String inputFilePath = "D:\\Users\\cs\\tmp\\input\\test.mp4";
		String outputFilePath = "D:\\tmp\\video\\test.jpg";
//		test1(inputFilePath, outputFilePath);
//		test2(inputFilePath, outputFilePath);
//		test3(inputFilePath, outputFilePath);
//		test4(inputFilePath, outputFilePath);
		
		String inputRtspPath = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
//		test1(inputRtspPath, outputFilePath);
		// read() Error: Could not read frame in start(). OpenCVFrameGrabber
//		test2(inputRtspPath, outputFilePath);
//		test3(inputRtspPath, outputFilePath);
//		test4(inputRtspPath, outputFilePath);
		
		test5(inputFilePath, new File(outputFilePath).getParent());
	}
	
	private static void test5(String inputFilePath, String outputFilePath) throws IOException {
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputFilePath);
		frameGrabber.start();
		int frameNumPerSecond = 5;
		int fps = (int) (frameGrabber.getFrameRate() / frameNumPerSecond);
		Loader.load(opencv_java.class);
		OpenCVFrameConverter.ToOrgOpenCvCoreMat frameConverter = new OpenCVFrameConverter.ToOrgOpenCvCoreMat();
		for (int i = 1; i <= 5000; i++) {
			Frame frame = frameGrabber.grabImage();
			if (frameGrabber.getFrameNumber() % fps == 0) {
				System.out.println(frameGrabber.getFrameNumber() + " " + frameGrabber.getTimestamp() * 1e-6);
				Mat mat = frameConverter.convert(frame);
				Imgcodecs.imwrite(String.format("%s\\test-%d.jpg", outputFilePath, i), mat);
			}
		}
		frameGrabber.close();
	}
	
	private static void test4(String inputFilePath, String outputFilePath) throws IOException {
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputFilePath);
		frameGrabber.start();
		Frame frame = frameGrabber.grabImage();
		Loader.load(opencv_java.class);
		OpenCVFrameConverter.ToOrgOpenCvCoreMat frameConverter = new OpenCVFrameConverter.ToOrgOpenCvCoreMat();
		Mat mat = frameConverter.convert(frame);
		Imgcodecs.imwrite(outputFilePath, mat);
		frameGrabber.close();
	}

	private static void test3(String inputFilePath, String outputFilePath) throws IOException {
		OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(inputFilePath);
		frameGrabber.start();
		Frame frame = frameGrabber.grab();
		Loader.load(opencv_java.class);
		OpenCVFrameConverter.ToOrgOpenCvCoreMat frameConverter = new OpenCVFrameConverter.ToOrgOpenCvCoreMat();
		Mat mat = frameConverter.convert(frame);
		Imgcodecs.imwrite(outputFilePath, mat);
		frameGrabber.close();
	}

	private static void test2(String inputFilePath, String outputFilePath) throws IOException {
		OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(inputFilePath);
		frameGrabber.start();
		Frame frame = frameGrabber.grab();
		Java2DFrameConverter frameConverter = new Java2DFrameConverter();
		BufferedImage bufferedImage = frameConverter.convert(frame);
		File outputImageFile = new File(outputFilePath);
		ImageIO.write(bufferedImage, "jpg", outputImageFile);
		frameGrabber.close();
	}

	private static void test1(String inputFilePath, String outputFilePath) throws Exception, IOException {
		FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputFilePath);
		frameGrabber.start();
		Frame frame = frameGrabber.grabImage();
		Java2DFrameConverter frameConverter = new Java2DFrameConverter();
		BufferedImage bufferedImage = frameConverter.convert(frame);
		File outputImageFile = new File(outputFilePath);
		ImageIO.write(bufferedImage, "jpg", outputImageFile);
		frameGrabber.close();
	}

}
