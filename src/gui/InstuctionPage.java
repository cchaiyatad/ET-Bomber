package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import setting.Setting;

public class InstuctionPage extends FlowPane {
	public InstuctionPage() {
		Label instuctionLabel = new Label("Lorem ipsum dolor sit amet, cu sed atqui homero, "
				+ "eu idque civibus pri. Vim eu idque vivendo, te sea graeci equidem volutpat. "
				+ "Eam at tale melius, cu dico paulo legendos his. Per eligendi deleniti ea, "
				+ "efficiendi neglegentur contentiones sit in. Te erant appellantur ius, nam "
				+ "cu expetenda qualisque assentior, quaestio ocurreret necessitatibus no has.\n" + "\n"
				+ "Sit natum platonem mnesarchum ne, sit justo solet dissentiet ex, vim virtute "
				+ "democritum in. Error invidunt ei pri, ut admodum voluptaria eam, ius persius "
				+ "disputationi ei. Ut sed omnes mentitum, diam illud persecuti sit eu. Iriure "
				+ "deterruisset reprehendunt in quo, te vel paulo postea pertinacia, "
				+ "elitr accusata intellegebat ut cum.");

		instuctionLabel.setWrapText(true);
		instuctionLabel.setPrefSize(Setting.SCENE_WIDTH - 100, Setting.SCENE_HEIGHT - 100);
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, null, null)));
		this.getChildren().add(instuctionLabel);
	}
}
