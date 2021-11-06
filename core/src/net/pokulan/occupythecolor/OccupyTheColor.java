package net.pokulan.occupythecolor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OccupyTheColor extends ApplicationAdapter implements GestureDetector.GestureListener , InputProcessor {
	SpriteBatch batch;
	Texture img;
	Texture d_pad;
	Texture shadow_dark;
	Texture shadow_world_light;
	Sprite shadow_player;
	Sprite[] particles_water = new Sprite[4];
	Sprite[] particles_mud = new Sprite[4];
	Texture shalow_shader;
	Sprite candle;
	Texture dark;
	View view_current;
	Field[] fields = new Field[8];
	FieldColor[] all_colors = new FieldColor[8];

	public Map<String, View> views = new HashMap<String, View>();
	ScoreRow[] tabela_wynikow = new ScoreRow[10];

	Preferences prefs;
	public AudioModule audioModule;
	GestureDetector detector;
	InputProcessor inpProcessor;

	// SYSTEM VARS
	public int gameScreenX = 160, gameScreenY = 90;
	public int tapX = 0, tapY = 0;
	int fling_direction = 0;
	public float ResX = 0;
	public float ResY = 0;
	public float screen_x_shift, screen_y_shift;
	public int dialogBox = 0;
	public String text_buffer = "";
	boolean PC;
	String game_id = "";
	String current_view_str;
	Random x;
	public int save_number = 0;
	int[][] player_sets = {
			{0, 0, 0, 35, 1, 0},
			{1, 2, 2, 37, 0, 1000},
			{2, 13, 3, 40, 0, 2200},
			{3, 7, 1, 43, 0, 4800},
			{4, 18, 5, 45, 0, 8999}};

	// SETTINGS
	public int audioEnable = 2;
	final int  BODY_SKINS = 5;
	final int HAIR_SKINS = 20;
	final int EYES_SKINS = 6;
	boolean easy_mode = false;

	// VIEWS CONSTANS
	int VIEW_SPLASH_SCREEN = 0;
	int VIEW_MAIN_MENU = 1;

	// VERSION SECTION
	boolean DEBUG = true;
	String appVersion = "0.0.1";
	int appVersionNumber = 0;

	Map<Integer, WalkParticles> walk_particles = new HashMap<Integer, WalkParticles>();

	// OBJECTS
	CharSkin skins;
	Player player;
	String P_name = "";
	Player[] other_player = new Player[11];
	Player[] other_player_copy = new Player[11];
	int how_many_players = 0;
	int time_max = 1500;
	int time_current = time_max;
	int proba = 0;
	int punkty = 0;
	int timer_last = 0;
	int rand_color = 0;
	int player_set = 0;
	int coins = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		shalow_shader = new Texture("maps/objects/shalow_shader.png");
		shadow_dark = new Texture("maps/lights/dark.png");
		shadow_world_light = new Texture("maps/lights/whole_spot.png");
		d_pad = new Texture("gui/d_pad.png");
		candle = new Sprite(new Texture("candle.png"));

		dark = new Texture("maps/lights/dark.png");

		ResX = proporcje('x');
		ResY = proporcje('y');

		detector = new GestureDetector(this);
		if(PC == false) Gdx.input.setInputProcessor(detector);
		else Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		audioModule = new AudioModule(this);
		prefs = Gdx.app.getPreferences("Prefs");
		P_name = loadString("player_name", "player");
		x = new Random();
		audioEnable = loadInt("audioEnable", 2);
		easy_mode = loadBoolean("easy_mode", false);
		player_set = loadInt("player_set", 0);
		coins = loadInt("coins", 0);
		for(int i = 0; i < tabela_wynikow.length; i++){
			if(loadBoolean("score_i" + i, false)) tabela_wynikow[i] = new ScoreRow(loadString("score_n" + i, ""), loadString("score_s" + i, ""));
			else tabela_wynikow[i] = new ScoreRow();
		}
		for(int i = 1; i < player_sets.length; i++) player_sets[i][4] = loadInt("secik" + i, 0);

		// CREATE VIEWS
		views.put(ViewSplash.NAME, new ViewSplash(batch, this));
		views.put(ViewMainMenu.NAME, new ViewMainMenu(batch, this));
		views.put(ViewLobby.NAME, new ViewLobby(batch, this));
		changeView(ViewSplash.NAME);

		player = new Player(player_sets[player_set][0], view_current, this);
		player.setHairs(player_sets[player_set][1]);
		player.setEyes(player_sets[player_set][2]);
		player.setSpeed(player_sets[player_set][3]);
		player.setName(P_name);
		player.id = 1;

		for(int i = 0; i < 4; i++) {
			particles_water[i] = new Sprite(new Texture("maps/particles/water_" + i + ".png"));
			particles_water[i].setScale(view_current.screen_x_multipler, view_current.screen_y_multipler);
			particles_water[i].setOrigin(0.5f,0.5f);
			particles_mud[i] = new Sprite(new Texture("maps/particles/mud_" + i + ".png"));
			particles_mud[i].setScale(view_current.screen_x_multipler, view_current.screen_y_multipler);
			particles_mud[i].setOrigin(0.5f,0.5f);
		}

		for(int i = 0; i < 10; i++){
			other_player[i] = new Player(0, view_current, this);
		}

		skins =  new CharSkin(BODY_SKINS, BODY_SKINS, HAIR_SKINS, EYES_SKINS, view_current);
		for(int i = 0; i < BODY_SKINS; i++){
			skins.setStatic(i, new Sprite(new Texture("characters/"+i+"/"+i+"_body_static.png")));
			skins.setHead(i, new Sprite(new Texture("characters/"+i+"/"+i+"_head.png")));
			skins.setRun(i, new Animation<TextureRegion>(6, new TextureRegion(new Texture("characters/"+i+"/"+i+"_body_run_0.png")), new TextureRegion(new Texture("characters/"+i+"/"+i+"_body_run_1.png")), new TextureRegion(new Texture("characters/"+i+"/"+i+"_body_run_2.png")), new TextureRegion(new Texture("characters/"+i+"/"+i+"_body_run_3.png")), new TextureRegion(new Texture("characters/"+i+"/"+i+"_body_run_4.png"))));
		}
		for(int i = 0; i < HAIR_SKINS; i++){
			skins.setHairs(i, new Sprite(new Texture("characters/hairs/"+i+"_hair.png")));
		}
		for(int i = 0; i < EYES_SKINS; i++){
			skins.setEyes(i, new Sprite(new Texture("characters/eyes/"+i+"_eye.png")));
		}
		for(int i = 0; i < 10; i++){
			other_player[i] = new Player(view_current, this);
			other_player_copy[i] = new Player(view_current, this);
		}

		fields[0] = new Field(3, 71, this);
		fields[1] = new Field(6, 8, this);
		fields[2] = new Field(36, 35, this);
		fields[3] = new Field(58, 62, this);
		fields[4] = new Field(67, 12, this);
		fields[5] = new Field(102, 46, this);
		fields[6] = new Field(135, 73, this);
		fields[7] = new Field(137, 6, this);
		all_colors[0] = new FieldColor(Color.CYAN, "Cyjan");
		all_colors[1] = new FieldColor(Color.TEAL, "Morski");
		all_colors[2] = new FieldColor(Color.MAGENTA, "Magenta");
		all_colors[3] = new FieldColor(Color.CHARTREUSE, "Chartreuse");
		all_colors[4] = new FieldColor(Color.GOLDENROD, "Goldenrod");
		all_colors[5] = new FieldColor(Color.LIME, "Limonka");
		all_colors[6] = new FieldColor(Color.MAROON, "Kasztanowy");
		all_colors[7] = new FieldColor(Color.SALMON, "Łososiowy");
		reload_colors();
		candle.setOrigin(75, 75);
		candle.setScale(ResX, ResY);

	}

	@Override
	public void render () {
		view_current.render();
		view_current.touch();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void reload_colors(){
		shuffleArray(all_colors);
		for(int i = 0; i < fields.length; i ++){
			fields[i].set_color(all_colors[i]);
		}
	}

	static void shuffleArray(FieldColor[] ar)
	{
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			// Simple swap
			FieldColor a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	void put_highscore(String name, int score){
		String help_s = "";
		String help_i = "";
		boolean help_b = false;
		int found = -1;
		for(int i = 0; i < tabela_wynikow.length; i++){
			if(found == -1 && Integer.parseInt(tabela_wynikow[i].getScore()) <= score){
				found = i;
				help_s = tabela_wynikow[i].getName();
				help_i = tabela_wynikow[i].getScore();
				help_b = tabela_wynikow[i].getIs();
				tabela_wynikow[i].setName(name);
				tabela_wynikow[i].setScore(score);
				tabela_wynikow[i].setIs(true);
				break;
			}
		}
		if(found > -1) {
			for (int i = tabela_wynikow.length - 1; i > found; i--) {
				if(i > found + 1){
					tabela_wynikow[i].setName(tabela_wynikow[i - 1].getName());
					tabela_wynikow[i].setScore(tabela_wynikow[i - 1].getScore());
					tabela_wynikow[i].setIs(tabela_wynikow[i - 1].getIs());
				}else{
					tabela_wynikow[i].setName(help_s);
					tabela_wynikow[i].setScore(help_i);
					tabela_wynikow[i].setIs(help_b);
				}
			}
		}
		for(int i = 0; i < tabela_wynikow.length; i++){
			saveBoolean("score_i" + i, tabela_wynikow[i].getIs());
			saveString("score_n" + i, tabela_wynikow[i].getName());
			saveString("score_s" + i, tabela_wynikow[i].getScore());
		}
	}

	public void changeView(String x){
		view_current = views.get(x);
		current_view_str = x;
		view_current.startUp();
	}

	public float scr(char c){
		if(c == 'x') return Gdx.graphics.getWidth();
		if(c == 'y') return Gdx.graphics.getHeight();

		return 0;
	}

	public float proporcje(char c){
		if(c == 'x') return (scr('x')/gameScreenX);
		if(c == 'y') return (scr('y')/gameScreenY);

		return 0;
	}

	public void saveLong(String key, long a){
		prefs.putLong(key, a);
		prefs.putString(key+"M5", Integer.toString((Long.toString(a) + key).hashCode()));
		prefs.flush();
	}

	public void saveInt(String key, int a){
		prefs.putInteger(key, a);
		prefs.putString(key+"M5", Integer.toString((Integer.toString(a) + key).hashCode()));
		prefs.flush();
	}
	public void saveString(String key, String a){
		prefs.putString(key, a);
		prefs.putString(key+"M5", Integer.toString((a + key).hashCode()));
		prefs.flush();
	}
	public void saveFloat(String key, Float a){
		prefs.putFloat(key, a);
		prefs.putString(key+"M5", Integer.toString((Double.toString(round(a,2)) + key).hashCode()));
		prefs.flush();
	}
	public void saveBoolean(String key, boolean a){
		prefs.putBoolean(key, a);
		prefs.putString(key+"M5", Integer.toString((Boolean.toString(a) + key).hashCode()));
		prefs.flush();
	}

	public long loadLong(String key, long val){
		long wynik = prefs.getLong(key, val);
		if(Integer.toString((Long.toString(wynik) + key).hashCode()).equals( prefs.getString(key+"M5", ""))){
			return wynik;
		}else return val;
	}
	public int loadInt(String key, int val){
		int wynik = prefs.getInteger(key, val);
		if(Integer.toString((Integer.toString(wynik) + key).hashCode()).equals( prefs.getString(key+"M5", ""))){
			return wynik;
		}else return val;
	}
	public String loadString(String key, String val){
		String wynik = prefs.getString(key, val);
		if(Integer.toString((wynik + key).hashCode()).equals( prefs.getString(key+"M5", ""))){
			return wynik;
		}else return val;
	}
	public float loadFloat(String key, float val){
		float wynik = prefs.getFloat(key, val);
		if( Integer.toString((Double.toString(round(wynik,2)) + key).hashCode()).equals( prefs.getString(key+"M5", ""))){
			return wynik;
		}else return val;
	}
	public boolean loadBoolean(String key, boolean val){
		boolean wynik = prefs.getBoolean(key, val);
		if(Integer.toString((Boolean.toString(wynik) + key).hashCode()).equals( prefs.getString(key+"M5", ""))){
			return wynik;
		}else return val;
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@java.lang.Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@java.lang.Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@java.lang.Override
	public boolean keyTyped(char character) {
		return false;
	}

	@java.lang.Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@java.lang.Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		tapX = Gdx.input.getX();
		tapY = Gdx.input.getY();
		return false;
	}

	@java.lang.Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@java.lang.Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@java.lang.Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	@java.lang.Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@java.lang.Override
	public boolean tap(float x, float y, int count, int button) {
		tapX = Gdx.input.getX();
		tapY = Gdx.input.getY();
		return false;
	}

	@java.lang.Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@java.lang.Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if(Math.abs(velocityX)>Math.abs(velocityY)) {
			if (velocityX > 1000) {
				fling_direction = 1;// RIGHT
			} else if (velocityX < -1000){
				fling_direction = 2; // LEFT
			}
		}
		return false;
	}

	@java.lang.Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@java.lang.Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@java.lang.Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@java.lang.Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@java.lang.Override
	public void pinchStop() {

	}
}
