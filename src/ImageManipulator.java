import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class ImageManipulator {
	BufferedImage input = null;
	BufferedImage output = null; 

	public ImageManipulator (String image){
		try{
			this.input = ImageIO.read(new File(image));
		} catch (IOException e) {
			System.out.println("Image not found");
		}
		this.output = new BufferedImage(this.input.getWidth(), this.input.getHeight(), 1);

	}

	public BufferedImage pixelPic(int p_size, BufferedImage in){
		for(int x=0;x<in.getWidth(); x+=p_size){
			for(int y=0;y<in.getHeight(); y+=p_size){
				int avg = this.averageColor(p_size, x, y, in);
				this.drawPixelBlock(p_size, avg, x, y);
			}
		}
		return this.output;
	}

	public int averageColor (int p_size,int x,int y, BufferedImage in){
		int avg_r =0;
		int avg_g =0;
		int avg_b =0;
		int avg_a =0;
		int count =0;
		int col;
		for(int i=x;i<x+p_size;i++){
			for(int j=y;j<y+p_size;j++){
				//Create a colour then get the rgb values of the color and the alph
				col = in.getRGB(i, j);
				avg_r += (col >> 16) & 0xff;
				avg_g += (col >> 8) & 0xff;
				avg_b += (col) & 0xff;
				avg_a += (col >> 24) & 0xff;
				count++;
			}
		}
		Color avg = new Color(avg_r/count,
				avg_g/count,
				avg_b/count,
				avg_a/count);
		return avg.getRGB();
	}

	public void drawPixelBlock(int p_size, int avg, int x,int y){
		for(int i=x;i<x+p_size;i++){
			for(int j=y;j<y+p_size;j++){
				this.output.setRGB(i, j, avg);

			}
		}
	}

	public BufferedImage alt_blackWhitePic(BufferedImage in){
		int w,x,y,col;
		int r,a;
		for(int i=0;i<in.getWidth();i++){
			for(int j=0;j<in.getHeight();j++){
				col = in.getRGB(i, j);
				w = (col >> 16) & 0xff;
				x = (col >> 8) & 0xff;
				y = (col) & 0xff;
				a = (col >> 24) & 0xff;
				
				r = ((w * 30)/100) + ((x *59)/100) + ((y * 11)/100);
				if(r>255)
					r = 255;
				Color avg = new Color(r,r,r,a);
				this.output.setRGB(i, j, avg.getRGB());
			}
		}
		return this.output;
	}
	
	public BufferedImage blackWhitePic(String input){
		File e = new File(input);        
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(e);
		} catch (IOException e1) {
			System.out.println("Failed to read image to convert to B&W");
		}        
		int w = bi.getWidth();        
		int h = bi.getHeight();
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = image.getGraphics();        
		g.drawImage(bi, 0, 0, null);
		return image;
	}
	
	public BufferedImage sepiaPic(BufferedImage in){
		int w,x,y,col;
		int r,g,b,a;
		for(int i=0;i<in.getWidth();i++){
			for(int j=0;j<in.getHeight();j++){
				col = in.getRGB(i, j);
				w = (col >> 16) & 0xff;
				x = (col >> 8) & 0xff;
				y = (col) & 0xff;
				a = (col >> 24) & 0xff;
				
				r = ((w * 393)/1000) + ((x *769)/1000) + ((y * 189)/1000);
				if(r>255)
					r = 255;
				g = ((w * 349)/1000) + ((x *686)/1000) + ((y * 168)/1000);
				if(g>255)
					g = 255;
				b = ((w * 272)/1000) + ((x *534)/1000) + ((y * 131)/1000);
				if(b>255)
					b = 255;
				
				Color avg = new Color(r,g,b,a);
				this.output.setRGB(i, j, avg.getRGB());
			}
		}
		return this.output;		
	}

	public BufferedImage negativePic(BufferedImage in){
		RescaleOp op = new RescaleOp(-1.0f, 255f, null); 
		BufferedImage negative = op.filter(in, null);
		return negative;
	}
	
	public BufferedImage boxBlurPic(int box_size,BufferedImage in){
		for(int i=box_size/2;i<in.getWidth()-box_size/2;i++){
			for(int j=box_size/2;j<in.getHeight()-box_size/2;j++){
				this.output.setRGB(i, j, this.averageColor(box_size, i-(box_size/2), j-(box_size/2), in));
			}
		}
		return this.output;
	}
	
	public void createJPEG(String name , BufferedImage image){
		try{
			File file = new File(name+".jpg");
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			System.out.println("Writing of file failed");
		}

	}

	public int findIdealPixelSize(int p_size){
		//Might want to do this method for variable sizes
		return 0;
	}
}
