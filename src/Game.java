//Gunnar Atchley
// 10/30/2020
import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model; //instance of model
	View view; //instance of view
	Controller controller; //instance of controller
	//Game constructor
	public Game()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		view.addMouseListener(controller);
		this.setTitle("Tubetacular!"); //JFrame is game window sets title
		this.setSize(800, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addKeyListener(controller);
		Json j = Json.load("map.Json");
		model.unmarshal(j);
	}
	
	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 miliseconds
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			

		}
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}


