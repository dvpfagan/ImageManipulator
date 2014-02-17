/**
 * 
 * @author Dave
 *
 */
public class Run {

	/**
	 * Just feed in some jpeg's as args and the code will create some
	 * interesting images
	 * @param args
	 */
	public static void main(String[] args) {  
		String[] image = args;
		for(String s : image){
			System.out.println("Reading File");
			ImageManipulator img = new ImageManipulator(s +".jpg");
			System.out.println("Making Pixel Pic");
			img.createJPEG("pixelpic_"+s, img.pixelPic(10, img.input));
			System.out.println("Making Sepia Pic");
			img.createJPEG("sepiaPic_"+s, img.sepiaPic(img.input));
			System.out.println("Making Negative Pic");
			img.createJPEG("negPic_"+s, img.negativePic(img.input));
			System.out.println("Making BLack and White Pics");
			img.createJPEG("alt_bwpic_"+s, img.alt_blackWhitePic(img.input));
			img.createJPEG("bwpic_"+s, img.blackWhitePic(s+".jpg"));
			System.out.println("Making Hybrid Pics");
			img.createJPEG("hybrid1_"+s, img.pixelPic(10, img.alt_blackWhitePic(img.input)));
			img.createJPEG("hybrid2_"+s, img.pixelPic(10, img.negativePic(img.input)));
			img.createJPEG("hybrid3_"+s, img.sepiaPic(img.negativePic(img.input)));
			img.createJPEG("boxblurpic_"+s, img.boxBlurPic(5,img.input));
		}
		System.out.print("Enjoy your pics");
	}
}
