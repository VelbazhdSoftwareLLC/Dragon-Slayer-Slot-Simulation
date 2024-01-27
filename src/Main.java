/*==============================================================================
*                                                                              *
* Dragon Slayer Simulator version 1.0.0                                        *
* Copyrights (C) 2024 Velbazhd Software LLC                                    *
*                                                                              *
* developed by Todor Balabanov ( todor.balabanov@gmail.com )                   *
* Sofia, Bulgaria                                                              *
*                                                                              *
==============================================================================*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.security.SecureRandom;

/** Application entry point class. */
public class Main {
	/** Pseudo-random number generator instance. */
	private static final Random PRNG = new SecureRandom();

	/** The names of the symbols. */
	private static final String SYMBOL_NAMES[] = { "Pouch", "Claw", "Sword", "Shield", "Tome", "Crystal", "Knight",
			"Wizard", "Huntress", "Wild", "Scatter", };

	// TODO The paytable was not specified in the initial software requirements
	// specification.
	/**
	 * The paytable of the game. The first index in the number of consequent
	 * symbols. The second index is the index of the symbol in the symbols list.
	 */
	private static final int PAYTABLE[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2000 }, { 20, 20, 20, 40, 40, 40, 80, 80, 80, 320, 3000 },
			{ 40, 40, 40, 80, 80, 80, 160, 160, 160, 640, 0 }, { 80, 80, 80, 160, 160, 160, 320, 320, 320, 1280, 0 }, };

	// TODO The lines were not specified in the initial software requirements
	// specification.
	// TODO The lines were not specified to be from left to right in the initial
	// software requirements specification.
	/** Win lines configurations */
	private static final int LINES[][] = { { 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2 }, { 0, 1, 2, 1, 0 },
			{ 2, 1, 0, 1, 2 }, { 2, 2, 1, 2, 2 }, { 0, 0, 1, 0, 0 }, { 1, 2, 2, 2, 1 }, { 1, 0, 0, 0, 1 },
			{ 1, 2, 1, 0, 1 }, };

	/** Number of rows on the screen. */
	private static final int NUMBER_OF_ROWS = 3;

	/** Number of columns on the screen. */
	private static final int NUMBER_OF_COLUMNS = 5;

	/** Number of free spins rewarded. */
	private static final int NUMBER_OF_FREE_SPINS_REWARD = 10;

	/** Array with symbol indices as virtual base game reels. */
	private static final int BASE_GAME_REELS[][] = new int[NUMBER_OF_COLUMNS][];

	/** Array with symbols indices as virtual free spins reels. */
	private static final int FREE_SPINS_REELS[][] = new int[NUMBER_OF_COLUMNS][];

	/** The text representation of the base game reels. */
	private static final String BASE_GAME_REELS_AS_TEXT = ("Huntress	Crystal	Knight	Shield	Sword\r\n"
			+ "Shield	Scatter	Tome	Sword	Sword\r\n" + "Wizard	Tome	Claw	Claw	Crystal\r\n"
			+ "Shield	Claw	Wizard	Sword	Tome\r\n" + "Sword	Shield	Crystal	Crystal	Claw\r\n"
			+ "Tome	Sword	Knight	Huntress	Shield\r\n" + "Sword	Shield	Claw	Sword	Crystal\r\n"
			+ "Huntress	Crystal	Crystal	Sword	Claw\r\n" + "Claw	Knight	Tome	Sword	Shield\r\n"
			+ "Sword	Claw	Wild	Knight	Shield\r\n" + "Huntress	Shield	Claw	Sword	Shield\r\n"
			+ "Pouch	Claw	Huntress	Claw	Crystal\r\n" + "Shield	Crystal	Claw	Tome	Claw\r\n"
			+ "Crystal	Shield	Sword	Claw	Claw\r\n" + "Claw	Shield	Wizard	Sword	Claw\r\n"
			+ "Huntress	Wild	Sword	Sword	Sword\r\n" + "Sword	Claw	Sword	Shield	Claw\r\n"
			+ "Shield	Sword	Knight	Sword	Pouch\r\n" + "Tome	Crystal	Sword	Crystal	Shield\r\n"
			+ "Claw	Shield	Sword	Sword	Wizard\r\n" + "Sword	Knight	Sword	Claw	Sword\r\n"
			+ "Pouch	Claw	Tome	Sword	Sword\r\n" + "Shield	Sword	Claw	Sword	Sword\r\n"
			+ "Tome	Tome	Pouch	Claw	Sword\r\n" + "Crystal	Claw	Sword	Claw	Wizard\r\n"
			+ "Sword	Crystal	Sword	Tome	Shield\r\n" + "Shield	Claw	Wizard	Scatter	Sword\r\n"
			+ "Knight	Crystal	Knight	Claw	Crystal\r\n" + "Claw	Sword	Claw	Crystal	Claw\r\n"
			+ "Huntress	Claw	Shield	Claw	Wizard\r\n" + "Knight	Wizard	Wizard	Scatter	Tome\r\n"
			+ "Tome	Crystal	Crystal	Claw	Shield\r\n" + "Claw	Sword	Claw	Tome	Shield\r\n"
			+ "Pouch	Claw	Tome	Shield	Pouch\r\n" + "Crystal	Sword	Knight	Shield	Claw\r\n"
			+ "Tome	Crystal	Shield	Sword	Sword\r\n" + "Shield	Tome	Sword	Wizard	Tome\r\n"
			+ "Knight	Shield	Sword	Claw	Tome\r\n" + "Claw	Wizard	Knight	Claw	Sword\r\n"
			+ "Tome	Sword	Tome	Claw	Sword\r\n" + "Sword	Crystal	Claw	Sword	Sword\r\n"
			+ "Tome	Claw	Shield	Sword	Tome\r\n" + "Shield	Scatter	Knight	Shield	Sword\r\n"
			+ "Sword	Sword	Sword	Shield	Claw\r\n" + "Tome	Crystal	Wizard	Claw	Pouch\r\n"
			+ "Pouch	Shield	Knight	Claw	Claw\r\n" + "Tome	Claw	Crystal	Sword	Knight\r\n"
			+ "Claw	Shield	Pouch	Sword	Claw\r\n" + "Sword	Wizard	Claw	Sword	Pouch\r\n"
			+ "Tome	Sword	Sword	Tome	Claw\r\n" + "Wizard	Knight	Wizard	Shield	Shield\r\n"
			+ "Knight	Shield	Shield	Tome	Sword\r\n" + "Shield	Sword	Tome	Knight	Sword\r\n"
			+ "Claw	Shield	Sword	Claw	Claw\r\n" + "Tome	Crystal	Sword	Crystal	Shield\r\n"
			+ "Huntress	Claw	Crystal	Tome	Crystal\r\n" + "Pouch	Knight	Claw	Tome	Tome\r\n"
			+ "Tome	Tome	Claw	Knight	Sword\r\n" + "Sword	Tome	Pouch	Shield	Claw\r\n"
			+ "Wizard	Crystal	Sword	Crystal	Tome\r\n" + "Tome	Sword	Claw	Knight	Knight\r\n"
			+ "Huntress	Claw	Shield	Shield	Claw\r\n" + "Claw	Huntress	Tome	Claw	Tome\r\n"
			+ "Sword	Claw	Claw	Shield	Shield\r\n" + "Pouch	Wizard	Knight	Shield	Shield\r\n"
			+ "Claw	Sword	Scatter	Sword	Claw\r\n" + "Crystal	Wizard	Tome	Crystal	Sword\r\n"
			+ "Tome	Sword	Knight	Claw	Claw\r\n" + "Shield	Claw	Shield	Claw	Pouch\r\n"
			+ "Wild	Wizard	Wizard	Claw	Shield\r\n" + "Wizard	Crystal	Shield	Sword	Tome\r\n"
			+ "Claw	Wizard	Crystal	Claw	Claw\r\n" + "Sword	Claw	Sword	Shield	Crystal\r\n"
			+ "Knight	Shield	Claw	Claw	Claw\r\n" + "Huntress	Shield	Pouch	Shield	Sword\r\n"
			+ "Shield	Shield	Sword	Shield	Sword\r\n" + "Crystal	Claw	Shield	Claw	Claw\r\n"
			+ "Huntress	Sword	Huntress	Tome	Crystal\r\n" + "Tome	Wizard	Knight	Sword	Sword\r\n"
			+ "Sword	Sword	Crystal	Sword	Shield\r\n" + "Claw	Shield	Wizard	Claw	Shield\r\n"
			+ "Pouch	Wizard	Sword	Sword	Pouch\r\n" + "Crystal	Shield	Claw	Tome	Sword\r\n"
			+ "Shield	Wizard	Pouch	Shield	Tome\r\n" + "Claw	Tome	Tome	Claw	Sword\r\n"
			+ "Crystal	Crystal	Crystal	Claw	Claw\r\n" + "Claw	Knight	Scatter	Knight	Crystal\r\n"
			+ "Sword	Tome	Sword	Claw	Shield\r\n" + "Shield	Sword	Crystal	Sword	Tome\r\n"
			+ "Huntress	Claw	Claw	Claw	Sword\r\n" + "Claw	Shield	Knight	Shield	Wild\r\n"
			+ "Tome	Sword	Claw	Shield	Sword\r\n" + "Crystal	Tome	Tome	Claw	Shield\r\n"
			+ "Pouch	Crystal	Crystal	Claw	Crystal\r\n" + "Sword	Shield	Claw	Tome	Claw\r\n"
			+ "Tome	Tome	Shield	Crystal	Claw\r\n" + "Crystal	Crystal	Huntress	Sword	Tome\r\n"
			+ "Tome	Sword	Knight	Claw	Pouch\r\n" + "Claw	Wizard	Claw	Claw	Claw\r\n"
			+ "Tome	Claw	Huntress	Wild	Knight\r\n" + "Pouch	Shield	Sword	Sword	Sword\r\n"
			+ "Huntress	Claw	Claw	Crystal	Knight\r\n" + "Shield	Claw	Pouch	Tome	Claw\r\n"
			+ "Shield	Shield	Sword	Wizard	Pouch\r\n" + "Shield	Tome	Sword	Tome	Claw\r\n"
			+ "Claw	Claw	Knight	Crystal	Sword\r\n" + "Huntress	Tome	Sword	Sword	Sword\r\n"
			+ "Claw	Claw	Knight	Claw	Tome\r\n" + "Shield	Wizard	Claw	Sword	Knight\r\n"
			+ "Tome	Claw	Shield	Knight	Crystal\r\n" + "Wizard	Sword	Huntress	Claw	Claw\r\n"
			+ "Tome	Claw	Shield	Claw	Pouch\r\n" + "Claw	Crystal	Knight	Claw	Claw\r\n"
			+ "Huntress	Shield	Scatter	Sword	Claw\r\n" + "Shield	Crystal	Sword	Wizard	Huntress\r\n"
			+ "Claw	Tome	Shield	Shield	Sword\r\n" + "Tome	Sword	Sword	Claw	Sword\r\n"
			+ "Huntress	Claw	Claw	Tome	Shield\r\n" + "Tome	Sword	Knight	Claw	Tome\r\n"
			+ "Sword	Shield	Claw	Claw	Claw\r\n" + "Tome	Claw	Knight	Shield	Shield\r\n"
			+ "Tome	Crystal	Claw	Tome	Knight\r\n" + "Shield	Wizard	Pouch	Claw	Claw\r\n"
			+ "Sword	Tome	Claw	Crystal	Sword\r\n" + "Crystal	Claw	Claw	Shield	Shield\r\n"
			+ "Claw	Knight	Pouch	Shield	Tome\r\n" + "Pouch	Shield	Claw	Sword	Pouch\r\n"
			+ "Shield	Claw	Huntress	Shield	Shield\r\n" + "Tome	Claw	Sword	Sword	Shield\r\n"
			+ "Claw	Crystal	Sword	Claw	Claw\r\n" + "Shield	Tome	Pouch	Claw	Tome\r\n"
			+ "Knight	Tome	Tome	Shield	Sword\r\n" + "Sword	Claw	Sword	Tome	Shield\r\n" + "").trim()
			.replace("\r", "");

	/** The text representation of the free spins reels. */
	private static final String FREE_SPINS_REELS_AS_TEXT = ("Shield	Sword	Sword	Sword	Shield\r\n"
			+ "Huntress	Sword	Shield	Shield	Claw\r\n" + "Claw	Crystal	Wizard	Claw	Sword\r\n"
			+ "Pouch	Knight	Wizard	Claw	Wild\r\n" + "Claw	Shield	Claw	Shield	Sword\r\n"
			+ "Shield	Claw	Knight	Shield	Crystal\r\n" + "Claw	Scatter	Shield	Wizard	Shield\r\n"
			+ "Wizard	Claw	Claw	Crystal	Claw\r\n" + "Shield	Shield	Wild	Tome	Sword\r\n"
			+ "Crystal	Sword	Tome	Claw	Sword\r\n" + "Claw	Sword	Tome	Sword	Wizard\r\n"
			+ "Pouch	Knight	Sword	Tome	Sword\r\n" + "Claw	Tome	Shield	Shield	Sword\r\n"
			+ "Claw	Claw	Tome	Claw	Claw\r\n" + "Claw	Scatter	Shield	Shield	Wizard\r\n"
			+ "Claw	Sword	Crystal	Crystal	Sword\r\n" + "Wizard	Shield	Sword	Claw	Sword\r\n"
			+ "Claw	Claw	Tome	Claw	Claw\r\n" + "Sword	Scatter	Claw	Shield	Shield\r\n"
			+ "Pouch	Sword	Claw	Sword	Sword\r\n" + "Tome	Crystal	Tome	Claw	Crystal\r\n"
			+ "Tome	Sword	Sword	Tome	Sword\r\n" + "Shield	Shield	Crystal	Sword	Crystal\r\n"
			+ "Claw	Sword	Scatter	Shield	Knight\r\n" + "Pouch	Tome	Tome	Claw	Sword\r\n"
			+ "Tome	Sword	Crystal	Scatter	Shield\r\n" + "Sword	Claw	Claw	Tome	Claw\r\n"
			+ "Pouch	Claw	Sword	Tome	Pouch\r\n" + "Sword	Sword	Claw	Shield	Sword\r\n"
			+ "Sword	Shield	Sword	Sword	Sword\r\n" + "Crystal	Claw	Claw	Claw	Claw\r\n"
			+ "Shield	Crystal	Shield	Shield	Shield\r\n" + "Wild	Claw	Shield	Shield	Sword\r\n"
			+ "Shield	Shield	Sword	Sword	Tome\r\n" + "Sword	Claw	Sword	Sword	Claw\r\n"
			+ "Claw	Tome	Shield	Knight	Crystal\r\n" + "Claw	Claw	Claw	Claw	Tome\r\n"
			+ "Knight	Shield	Claw	Sword	Claw\r\n" + "Claw	Claw	Huntress	Tome	Sword\r\n"
			+ "Huntress	Shield	Claw	Knight	Crystal\r\n" + "Sword	Claw	Sword	Shield	Sword\r\n"
			+ "Claw	Crystal	Sword	Crystal	Crystal\r\n" + "Sword	Sword	Knight	Sword	Crystal\r\n"
			+ "Sword	Shield	Claw	Knight	Crystal\r\n" + "Shield	Scatter	Scatter	Sword	Claw\r\n"
			+ "Claw	Crystal	Claw	Claw	Pouch\r\n" + "Sword	Shield	Knight	Knight	Claw\r\n"
			+ "Pouch	Sword	Crystal	Shield	Tome\r\n" + "Crystal	Shield	Shield	Sword	Tome\r\n"
			+ "Shield	Claw	Sword	Claw	Claw\r\n" + "Sword	Wizard	Shield	Claw	Pouch\r\n"
			+ "Claw	Knight	Tome	Sword	Claw\r\n" + "Claw	Sword	Shield	Tome	Sword\r\n"
			+ "Shield	Tome	Sword	Claw	Claw\r\n" + "Shield	Shield	Sword	Sword	Shield\r\n"
			+ "Sword	Wild	Claw	Claw	Sword\r\n" + "Sword	Shield	Tome	Crystal	Tome\r\n"
			+ "Claw	Claw	Tome	Sword	Shield\r\n" + "Sword	Shield	Shield	Huntress	Sword\r\n"
			+ "Crystal	Knight	Sword	Claw	Claw\r\n" + "Claw	Tome	Claw	Shield	Tome\r\n"
			+ "Tome	Claw	Sword	Knight	Shield\r\n" + "Tome	Sword	Knight	Claw	Shield\r\n"
			+ "Sword	Claw	Crystal	Claw	Sword\r\n" + "Claw	Tome	Claw	Sword	Claw\r\n"
			+ "Tome	Shield	Claw	Claw	Claw\r\n" + "Shield	Scatter	Shield	Shield	Shield\r\n"
			+ "Tome	Claw	Shield	Claw	Claw\r\n" + "Crystal	Claw	Tome	Claw	Claw\r\n"
			+ "Claw	Shield	Scatter	Scatter	Tome\r\n" + "Tome	Claw	Crystal	Claw	Claw\r\n"
			+ "Tome	Crystal	Sword	Claw	Claw\r\n" + "Sword	Claw	Claw	Knight	Claw\r\n"
			+ "Shield	Claw	Crystal	Shield	Claw\r\n" + "Claw	Sword	Shield	Claw	Claw\r\n"
			+ "Pouch	Crystal	Claw	Sword	Knight\r\n" + "Tome	Claw	Sword	Crystal	Claw\r\n"
			+ "Claw	Sword	Sword	Claw	Tome\r\n" + "Shield	Shield	Claw	Wizard	Claw\r\n"
			+ "Sword	Sword	Claw	Shield	Claw\r\n" + "Sword	Huntress	Sword	Sword	Sword\r\n"
			+ "Tome	Tome	Claw	Claw	Shield\r\n" + "Claw	Claw	Claw	Sword	Wizard\r\n"
			+ "Sword	Sword	Wild	Sword	Tome\r\n" + "Knight	Tome	Tome	Crystal	Claw\r\n"
			+ "Sword	Tome	Sword	Tome	Claw\r\n" + "Tome	Shield	Claw	Tome	Sword\r\n"
			+ "Sword	Sword	Sword	Sword	Shield\r\n" + "Tome	Claw	Sword	Claw	Shield\r\n"
			+ "Sword	Claw	Sword	Claw	Shield\r\n" + "Claw	Sword	Knight	Sword	Shield\r\n"
			+ "Crystal	Sword	Sword	Crystal	Tome\r\n" + "Knight	Tome	Sword	Wild	Claw\r\n"
			+ "Shield	Shield	Shield	Sword	Shield\r\n" + "Sword	Claw	Shield	Claw	Sword\r\n"
			+ "Crystal	Shield	Wizard	Crystal	Shield\r\n" + "Crystal	Sword	Knight	Claw	Tome\r\n"
			+ "Sword	Claw	Claw	Tome	Shield\r\n" + "Crystal	Sword	Shield	Shield	Claw\r\n"
			+ "Sword	Claw	Shield	Claw	Sword\r\n" + "Claw	Claw	Crystal	Sword	Claw\r\n"
			+ "Sword	Claw	Claw	Crystal	Sword\r\n" + "Sword	Knight	Claw	Sword	Sword\r\n"
			+ "Claw	Shield	Sword	Sword	Knight\r\n" + "Shield	Sword	Claw	Sword	Claw\r\n"
			+ "Tome	Sword	Tome	Wizard	Claw\r\n" + "Claw	Tome	Sword	Shield	Sword\r\n"
			+ "Wizard	Sword	Claw	Tome	Tome\r\n" + "Claw	Sword	Scatter	Sword	Shield\r\n"
			+ "Shield	Tome	Claw	Shield	Knight\r\n" + "Claw	Claw	Claw	Shield	Claw\r\n"
			+ "Wizard	Knight	Shield	Tome	Knight\r\n" + "Knight	Crystal	Sword	Claw	Shield\r\n"
			+ "Sword	Tome	Sword	Claw	Tome\r\n" + "Shield	Shield	Sword	Tome	Claw\r\n"
			+ "Claw	Wizard	Crystal	Claw	Sword\r\n" + "Claw	Claw	Shield	Scatter	Claw\r\n"
			+ "Sword	Claw	Crystal	Claw	Shield\r\n" + "Tome	Tome	Tome	Sword	Crystal\r\n"
			+ "Sword	Claw	Tome	Claw	Knight\r\n" + "Shield	Shield	Claw	Tome	Huntress\r\n"
			+ "Shield	Tome	Claw	Claw	Shield\r\n" + "Knight	Crystal	Tome	Tome	Claw\r\n"
			+ "Tome	Claw	Claw	Sword	Pouch\r\n" + "Claw	Claw	Tome	Claw	Sword\r\n"
			+ "Shield	Crystal	Sword	Crystal	Sword\r\n" + "Claw	Wizard	Claw	Sword	Crystal\r\n"
			+ "Shield	Sword	Claw	Shield	Shield\r\n" + "Claw	Claw	Shield	Shield	Claw\r\n"
			+ "Knight	Sword	Claw	Tome	Tome\r\n" + "Shield	Claw	Claw	Sword	Sword\r\n"
			+ "Sword	Tome	Claw	Claw	Tome\r\n" + "Crystal	Claw	Claw	Shield	Tome\r\n" + "").trim()
			.replace("\r", "");

	// TODO The game design document does not define how much the egg is healthy.
	/** Initial egg health during the base game mode. */
	private static final int INITIAL_EGG_HEALTH = 100;

	// TODO The game design document does not define how much the dragon is healthy.
	/** The dragon has three times more health points than the egg. */
	private static final int INITIAL_DRAGON_HEALTH = 3 * INITIAL_EGG_HEALTH;

	/** Base game multiplier. */
	private static int baseGameMultiplier = 1;

	/** Free spins multiplier with an alive dragon. */
	private static int aliveDragonMultiplier = 3;

	/** Presence of wild in the line wins multiplier. */
	private static int wildPresenceMultiplier = 2;

	/** Free spins multiplier with a dead dragon. */
	private static int deadDragonMultiplier = 3;

	// TODO When the dragon is slain, how will the egg appear?
	/** Is dragon alive flag. */
	private static boolean isDragonAlive = false;

	/** Egg health during the base game mode. */
	private static int eggHealth = 0;

	/** Dragon health during the free spins mode. */
	private static int dragonHealth = 0;

	/** Damage caused to the egg. */
	private static int eggDamage = 0;

	/** Damage done to the dragon. */
	private static int dragonDamage = 0;

	/** Single line bet in base game spin. */
	private static int singleLineBet = 1;

	/** Total bet in a single base game spin. */
	private static int totalBet = singleLineBet * LINES.length;

	/** Total amount of won money. */
	private static long wonMoney = 0;

	/** Total amount of lost money. */
	private static long lostMoney = 0;

	/** Total amount of won money in base game. */
	private static long baseMoney = 0;

	/** Total amount of won money in free spins. */
	private static long freeMoney = 0;

	/** Max amount of won money in base game. */
	private static long baseMaxWin = 0;

	/** Max amount of won money in free spins. */
	private static long freeMaxWin = 0;

	/** Total number of base games played. */
	private static long totalNumberOfGames = 0;

	/** Total number of free spins played. */
	private static long totalNumberOfFreeSpins = 0;

	/** Total number of free spins started. */
	private static long totalNumberOfFreeSpinsStarts = 0;

	/** Total number of free spins started. */
	private static long totalNumberOfFreeSpinsRestarts = 0;

	/** Hit rate of wins in base game. */
	private static long baseGameHitRate = 0;

	/** Hit rate of wins in free spins. */
	private static long freeSpinsHitRate = 0;

	/** Symbols win hit rate in base game. */
	private static long baseSymbolMoney[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, };

	/** Symbols hit rate in base game. */
	private static long baseGameSymbolsHitRate[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, };

	/** Symbols win hit rate in base game. */
	private static long freeSymbolMoney[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, };

	/** Symbols hit rate in base game. */
	private static long freeGameSymbolsHitRate[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, };

	/** Current visible symbols on the screen. */
	private static int view[][] = { { -1, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } };

	/** Number of free spins in the current base game spin. */
	private static int freeSpinsNumber = 0;

	/** Setup static class members. */
	static {
		/* Parse text of the base game reels. */ {
			List<String> values = new ArrayList<String>();
			for (String row : BASE_GAME_REELS_AS_TEXT.split("\n")) {
				values.addAll(Arrays.asList(row.split("\t")));
			}

			int length = values.size() / NUMBER_OF_COLUMNS;

			/* Fill reels structure. */
			for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
				BASE_GAME_REELS[i] = new int[length];
				for (int j = 0; j < length; j++) {
					BASE_GAME_REELS[i][j] = -1;
					for (int k = 0; k < SYMBOL_NAMES.length; k++) {
						if (values.get(i + NUMBER_OF_COLUMNS * j).equals(SYMBOL_NAMES[k]) == false) {
							continue;
						}

						BASE_GAME_REELS[i][j] = k;
						break;
					}
				}
			}
		}

		/* Parse text of the free spins reels. */ {
			List<String> values = new ArrayList<String>();
			for (String row : FREE_SPINS_REELS_AS_TEXT.split("\n")) {
				values.addAll(Arrays.asList(row.split("\t")));
			}

			int length = values.size() / NUMBER_OF_COLUMNS;

			/* Fill reels structure. */
			for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
				FREE_SPINS_REELS[i] = new int[length];
				for (int j = 0; j < length; j++) {
					FREE_SPINS_REELS[i][j] = -1;
					for (int k = 0; k < SYMBOL_NAMES.length; k++) {
						if (values.get(i + NUMBER_OF_COLUMNS * j).equals(SYMBOL_NAMES[k]) == false) {
							continue;
						}

						FREE_SPINS_REELS[i][j] = k;
						break;
					}
				}
			}
		}
	}

	/**
	 * Single reels spin to fill view with symbols.
	 *
	 * @param view  Visible screen.
	 * @param reels Reels strips.
	 */
	private static void spin(int view[][], int reels[][]) {
		for (int i = 0, r, u, d; i < view.length && i < reels.length; i++) {
			u = PRNG.nextInt(reels[i].length);
			r = u + 1;
			d = u + 2;

			r = r % reels[i].length;
			d = d % reels[i].length;

			view[i][0] = reels[i][u];
			view[i][1] = reels[i][r];
			view[i][2] = reels[i][d];
		}
	}

	/**
	 * Calculate win in particular line.
	 *
	 * @param line       Single line.
	 * @param multiplier Multiplier of the win.
	 *
	 * @return Calculated win.
	 */
	private static int lineWin(int line[], int multiplier) {
		/* Keep first symbol in the line. */
		int symbol = line[0];

		/* Wild symbol passing to find first regular symbol. */
		for (int i = 0; i < line.length; i++) {
			/* First no wild symbol found. he wild symbol is on index 9. */
			if (line[i] != 9) {
				/* Symbol on index 10 is not a regular symbol and do not form winning lines. */
				if (line[i] != 10) {
					symbol = line[i];
				}

				break;
			}
		}

		/*
		 * Wild symbol substitution.
		 */
		boolean wildPresence = false;
		for (int i = 0; i < line.length; i++) {
			/* The wild symbol is on index 9. */
			if (line[i] == 9) {
				/* Substitute wild with regular symbol. */
				line[i] = symbol;
				wildPresence = true;
			}
		}

		/* If the wild is presented in the line wins, the multiplier changes. */
		if (wildPresence == true) {
			multiplier *= wildPresenceMultiplier;
		}

		/* Count symbols in winning line. */
		int number = 0;
		for (int i = 0; i < line.length; i++) {
			if (line[i] == symbol) {
				number++;
			} else {
				break;
			}
		}

		/* Clear unused symbols. */
		for (int i = number; i < line.length; i++) {
			line[i] = -1;
		}

		int win = singleLineBet * PAYTABLE[number][symbol] * multiplier;

		// TODO The game design document does clarify how different win multipliers
		// influence damages.
		/* The egg and dragon take damage when a line wins form. */
		if (win > 0 && freeSpinsNumber == 0) {
			eggHealth -= PAYTABLE[number][symbol];

			baseSymbolMoney[number][symbol] += win;
			baseGameSymbolsHitRate[number][symbol]++;
		} else if (win > 0 && freeSpinsNumber > 0) {
			dragonHealth -= PAYTABLE[number][symbol];

			freeSymbolMoney[number][symbol] += win;
			freeGameSymbolsHitRate[number][symbol]++;
		}

		return (win);
	}

	/**
	 * Calculate win in all possible lines.
	 *
	 * @param view       Symbols visible in screen view.
	 * @param multiplier Multiplier of the win.
	 *
	 * @return Calculated win.
	 */
	private static int linesWin(int view[][], int multiplier) {
		int win = 0;

		/* Check wins in all possible lines. */
		for (int l = 0; l < LINES.length; l++) {
			int[] line = { -1, -1, -1, -1, -1 };

			/* Prepare line for combination check. */
			for (int i = 0; i < LINES[l].length; i++) {
				int index = LINES[l][i];
				line[i] = view[i][index];
			}

			/* Accumulate line win. */
			win += lineWin(line, multiplier);
		}

		return (win);
	}

	/**
	 * Calculate win from scatters.
	 *
	 * @param view       Visible screen.
	 * @param multiplier Multiplier of the win.
	 *
	 * @return Calculated win.
	 */
	private static int scatterWin(int view[][], int multiplier) {
		int numberOfScatters = 0;
		for (int i = 0; i < view.length; i++) {
			for (int j = 0; j < view[i].length; j++) {
				/* The scatter index is 10. */
				if (view[i][j] == 10) {
					numberOfScatters++;
				}
			}
		}

		/* The scatter index is 1. */
		int win = PAYTABLE[numberOfScatters][1] * totalBet * multiplier;

		if (win > 0 && freeSpinsNumber == 0) {
			/* The scatter index is 1. */
			baseSymbolMoney[numberOfScatters][10] += win;
			baseGameSymbolsHitRate[numberOfScatters][10]++;
		} else if (win > 0 && freeSpinsNumber > 0) {
			/* The scatter index is 1. */
			freeSymbolMoney[numberOfScatters][10] += win;
			freeGameSymbolsHitRate[numberOfScatters][10]++;
		}

		return (win);
	}

	/**
	 * Setup parameters for free spins mode.
	 * 
	 * @param view Visible screen.
	 */
	private static void freeSpinsSetup(int view[][]) {
		int numberOfScatters = 0;
		for (int i = 0; i < view.length; i++) {
			for (int j = 0; j < view[i].length; j++) {
				/* Scatter symbol is in index 10. */
				if (view[i][j] == 10) {
					numberOfScatters++;
				}
			}
		}

		/* In base game 3, scatters or a broken egg turn into free spins. */
		if (numberOfScatters < 3 && freeSpinsNumber == 0 && eggHealth > 0) {
			return;
		} else if (eggHealth <= 0 && freeSpinsNumber == 0) {
			freeSpinsNumber = NUMBER_OF_FREE_SPINS_REWARD;
			totalNumberOfFreeSpinsStarts++;
		} else if (numberOfScatters == 3 && freeSpinsNumber == 0) {
			freeSpinsNumber = NUMBER_OF_FREE_SPINS_REWARD;
			totalNumberOfFreeSpinsStarts++;
		} else if (numberOfScatters == 3 && freeSpinsNumber > 0 && isDragonAlive == true) {
			freeSpinsNumber += NUMBER_OF_FREE_SPINS_REWARD;
			totalNumberOfFreeSpinsRestarts++;
		}
	}

	/** Play single free spin game. */
	private static void singleFreeSpin() {
		/* Spin reels. */
		spin(view, FREE_SPINS_REELS);

		/* Win accumulated by lines. */
		int multiplier = isDragonAlive ? aliveDragonMultiplier : deadDragonMultiplier;
		int win = linesWin(view, multiplier) + scatterWin(view, multiplier);

		/* Add win to the statistics. */
		freeMoney += win;
		wonMoney += win;
		if (freeMaxWin < win) {
			freeMaxWin = win;
		}

		/* Count free games hit rate. */
		if (win > 0) {
			freeSpinsHitRate++;
		}

		/* Check for free games. */
		freeSpinsSetup(view);
	}

	/** Run single base game. */
	private static void singleBaseGame() {
		/*
		 * The egg regenerates its health completely at the beginning of each base game
		 * spin.
		 */
		eggHealth = INITIAL_EGG_HEALTH;

		/*
		 * The dragon regenerates its health completely at the beginning of each base
		 * game spin but does not regenerate its health points in each free spin.
		 */
		dragonHealth = INITIAL_DRAGON_HEALTH;

		/* The dragon is not born yet. */
		isDragonAlive = false;

		/* Spin reels. */
		spin(view, BASE_GAME_REELS);

		/* Win accumulated by lines. */
		int win = linesWin(view, baseGameMultiplier) + scatterWin(view, baseGameMultiplier);

		/* Add win to the statistics. */
		baseMoney += win;
		wonMoney += win;
		if (baseMaxWin < win) {
			baseMaxWin = win;
		}

		/* Count base game hit rate. */
		if (win > 0) {
			baseGameHitRate++;
		}

		/* Check for free games. */
		freeSpinsSetup(view);

		/* Play all free games. */
		while (freeSpinsNumber > 0) {
			totalNumberOfFreeSpins++;

			singleFreeSpin();

			freeSpinsNumber--;
		}
	}

	/** Print all simulation input data structures. */
	private static void printDataStructures() {
		System.out.println("Paytable:");
		for (int i = 0; i < PAYTABLE.length; i++) {
			System.out.print("\t" + i + " of");
		}
		System.out.println();
		for (int j = 0; j < PAYTABLE[0].length; j++) {
			System.out.print(SYMBOL_NAMES[j] + "\t");
			for (int i = 0; i < PAYTABLE.length; i++) {
				System.out.print(PAYTABLE[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Lines:");
		for (int i = 0; i < LINES.length; i++) {
			for (int j = 0; j < LINES[0].length; j++) {
				System.out.print(LINES[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Base Game Reels:");
		for (int i = 0; i < BASE_GAME_REELS.length; i++) {
			for (int j = 0; j < BASE_GAME_REELS[i].length; j++) {
				if (j % 10 == 0) {
					System.out.println();
				}
				System.out.print(SYMBOL_NAMES[BASE_GAME_REELS[i][j]] + " ");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Free Games Reels:");
		for (int i = 0; i < FREE_SPINS_REELS.length; i++) {
			for (int j = 0; j < FREE_SPINS_REELS[i].length; j++) {
				if (j % 10 == 0) {
					System.out.println();
				}
				System.out.print(SYMBOL_NAMES[FREE_SPINS_REELS[i][j]] + " ");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Base Game Reels:");
		/* Count symbols in reels. */
		{
			int[][] counters = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, };
			for (int i = 0; i < BASE_GAME_REELS.length; i++) {
				for (int j = 0; j < BASE_GAME_REELS[i].length; j++) {
					counters[i][BASE_GAME_REELS[i][j]]++;
				}
			}
			for (int i = 0; i < BASE_GAME_REELS.length; i++) {
				System.out.print("\tReel " + (i + 1));
			}
			System.out.println();
			for (int j = 0; j < counters[0].length; j++) {
				System.out.print(SYMBOL_NAMES[j] + "\t");
				for (int i = 0; i < counters.length; i++) {
					System.out.print(counters[i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println("---------------------------------------------");
			System.out.print("Total:\t");
			long combinations = 1L;
			for (int i = 0; i < counters.length; i++) {
				int sum = 0;
				for (int j = 0; j < counters[0].length; j++) {
					sum += counters[i][j];
				}
				System.out.print(sum + "\t");
				if (sum != 0) {
					combinations *= sum;
				}
			}
			System.out.println();
			System.out.println("---------------------------------------------");
			System.out.println("Combinations:\t" + combinations);
		}
		System.out.println();

		System.out.println("Free Games Reels:");
		/* Count symbols in reels. */
		{
			int[][] counters = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, };
			for (int i = 0; i < FREE_SPINS_REELS.length; i++) {
				for (int j = 0; j < FREE_SPINS_REELS[i].length; j++) {
					counters[i][FREE_SPINS_REELS[i][j]]++;
				}
			}
			for (int i = 0; i < FREE_SPINS_REELS.length; i++) {
				System.out.print("\tReel " + (i + 1));
			}
			System.out.println();
			for (int j = 0; j < counters[0].length; j++) {
				System.out.print(SYMBOL_NAMES[j] + "\t");
				for (int i = 0; i < counters.length; i++) {
					System.out.print(counters[i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println("---------------------------------------------");
			System.out.print("Total:\t");
			long combinations = 1L;
			for (int i = 0; i < counters.length; i++) {
				int sum = 0;
				for (int j = 0; j < counters[0].length; j++) {
					sum += counters[i][j];
				}
				System.out.print(sum + "\t");
				if (sum != 0) {
					combinations *= sum;
				}
			}
			System.out.println();
			System.out.println("---------------------------------------------");
			System.out.println("Combinations:\t" + combinations);
		}
		System.out.println();
	}

	/**
	 * Print simulation statistics.
	 */
	private static void printStatistics() {
		System.out.println("Won money:\t" + wonMoney);
		System.out.println("Lost money:\t" + lostMoney);
		System.out.println("Total Number of Games:\t" + totalNumberOfGames);
		System.out.println();
		System.out.println("Total RTP:\t" + ((double) wonMoney / (double) lostMoney) + "\t\t"
				+ (100.0D * (double) wonMoney / (double) lostMoney) + "%");
		System.out.println("Base Game RTP:\t" + ((double) baseMoney / (double) lostMoney) + "\t\t"
				+ (100.0D * (double) baseMoney / (double) lostMoney) + "%");
		System.out.println("Free Game RTP:\t" + ((double) freeMoney / (double) lostMoney) + "\t\t"
				+ (100.0D * (double) freeMoney / (double) lostMoney) + "%");
		System.out.println();
		System.out.println("Hit Frequency in Base Game:\t" + ((double) baseGameHitRate / (double) totalNumberOfGames)
				+ "\t\t" + (100.0D * (double) baseGameHitRate / (double) totalNumberOfGames) + "%");
		System.out
				.println("Hit Frequency in Free Game:\t" + ((double) freeSpinsHitRate / (double) totalNumberOfFreeSpins)
						+ "\t\t" + (100.0D * (double) freeSpinsHitRate / (double) totalNumberOfFreeSpins) + "%");
		System.out.println("Hit Frequency Base Game into Free Game:\t"
				+ ((double) totalNumberOfFreeSpinsStarts / (double) totalNumberOfGames) + "\t\t"
				+ (100.0D * (double) (totalNumberOfFreeSpinsStarts) / (double) totalNumberOfGames) + "%");
		System.out.println("Hit Frequency Free Game into Free Game:\t"
				+ ((double) totalNumberOfFreeSpinsRestarts / (double) totalNumberOfFreeSpinsStarts) + "\t\t"
				+ (100.0D * (double) (totalNumberOfFreeSpinsRestarts) / (double) totalNumberOfFreeSpinsStarts) + "%");
		System.out.println();
		System.out.println("Max Win in Base Game:\t" + baseMaxWin);
		System.out.println("Max Win in Free Game:\t" + freeMaxWin);

		System.out.println();
		System.out.println();
		System.out.println("Base Game Symbols RTP:");
		System.out.print("\t");
		for (int i = 0; i < baseSymbolMoney.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < baseSymbolMoney[0].length; j++) {
			System.out.print(SYMBOL_NAMES[j] + "\t");
			for (int i = 0; i < baseSymbolMoney.length; i++) {
				System.out.print((double) baseSymbolMoney[i][j] / (double) lostMoney + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Base Game Symbols Hit Rate:");
		System.out.print("\t");
		for (int i = 0; i < baseGameSymbolsHitRate.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < baseGameSymbolsHitRate[0].length; j++) {
			System.out.print(SYMBOL_NAMES[j] + "\t");
			for (int i = 0; i < baseGameSymbolsHitRate.length; i++) {
				System.out.print((double) baseGameSymbolsHitRate[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Base Game Symbols Hit Frequency:");
		System.out.print("\t");
		for (int i = 0; i < baseGameSymbolsHitRate.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < baseGameSymbolsHitRate[0].length; j++) {
			System.out.print(SYMBOL_NAMES[j] + "\t");
			for (int i = 0; i < baseGameSymbolsHitRate.length; i++) {
				System.out.print((double) baseGameSymbolsHitRate[i][j] / (double) totalNumberOfGames + "\t");
			}
			System.out.println();
		}

		System.out.println();
		System.out.println("Free Games Symbols RTP:");
		System.out.print("\t");
		for (int i = 0; i < freeSymbolMoney.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < freeSymbolMoney[0].length; j++) {
			System.out.print(SYMBOL_NAMES[j] + "\t");
			for (int i = 0; i < freeSymbolMoney.length; i++) {
				System.out.print((double) freeSymbolMoney[i][j] / (double) lostMoney + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Free Games Symbols Hit Frequency:");
		System.out.print("\t");
		for (int i = 0; i < freeGameSymbolsHitRate.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < freeGameSymbolsHitRate[0].length; j++) {
			System.out.print(SYMBOL_NAMES[j] + "\t");
			for (int i = 0; i < freeGameSymbolsHitRate.length; i++) {
				System.out.print((double) freeGameSymbolsHitRate[i][j] / (double) totalNumberOfGames + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Free Games Symbols Hit Rate:");
		System.out.print("\t");
		for (int i = 0; i < freeGameSymbolsHitRate.length; i++) {
			System.out.print("" + i + "of\t");
		}
		System.out.println();
		for (int j = 0; j < freeGameSymbolsHitRate[0].length; j++) {
			System.out.print(SYMBOL_NAMES[j] + "\t");
			for (int i = 0; i < freeGameSymbolsHitRate.length; i++) {
				System.out.print((double) freeGameSymbolsHitRate[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Print simulation execution command.
	 *
	 * @param args Command line arguments list.
	 */
	private static void printExecuteCommand(String[] args) {
		System.out.println("Execute command:");
		System.out.println();
		System.out.print("java Main ");
		for (int i = 0; i < args.length; i++) {
			System.out.print(args[i] + " ");
		}
		System.out.println();
	}

	/** Print about information. */
	private static void printAbout() {
		System.out.println("*******************************************************************************");
		System.out.println("*                                                                             *");
		System.out.println("* Dragon Slayer Simulator version 1.0.0                                       *");
		System.out.println("* Copyrights (C) 2024 Velbazhd Software LLC                                   *");
		System.out.println("*                                                                             *");
		System.out.println("* developed by Todor Balabanov ( todor.balabanov@gmail.com )                  *");
		System.out.println("* Sofia, Bulgaria                                                             *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
	}

	/** Print help information. */
	private static void printHelp() {
		System.out.println("*******************************************************************************");
		System.out.println("*                                                                             *");
		System.out.println("* -help           Help screen.                                                *");
		System.out.println("*                                                                             *");
		System.out.println("* -g<number>      Number of games (default 100 000 000).                      *");
		System.out.println("* -p<number>      Progress on each iteration number (default 100 000 000).    *");
		System.out.println("*                                                                             *");
		System.out.println("* -verify         Print input data structures.                                *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
	}

	/**
	 * Application single entry point method.
	 * 
	 * java Main -g100m -p10k
	 * 
	 * java Main -verify
	 * 
	 * java Main -help
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		printExecuteCommand(args);
		System.out.println();

		long numberOfSimulations = 100_000_000;
		long progressPrintOnIteration = 100_000_000;

		boolean verboseOutput = false;

		/* Parse command line arguments. */
		for (int a = 0; a < args.length; a++) {
			if (args.length > 0 && args[a].contains("-g")) {
				String parameter = args[a].substring(2);

				if (parameter.contains("k")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000";
				}

				if (parameter.contains("m")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000000";
				}

				try {
					numberOfSimulations = Integer.valueOf(parameter);
				} catch (Exception e) {
				}
			}

			if (args.length > 0 && args[a].contains("-p")) {
				String parameter = args[a].substring(2);

				if (parameter.contains("k")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000";
				}

				if (parameter.contains("m")) {
					parameter = parameter.substring(0, parameter.length() - 1);
					parameter += "000000";
				}

				try {
					progressPrintOnIteration = Integer.valueOf(parameter);
					verboseOutput = true;
				} catch (Exception e) {
				}
			}

			if (args.length > 0 && args[a].contains("-verify")) {
				printDataStructures();
				System.exit(0);
			}

			if (args.length > 0 && args[a].contains("-help")) {
				printAbout();
				printHelp();
				System.out.println();
				System.exit(0);
			}
		}

		/* Simulation main loop. */
		for (long g = 0; g < numberOfSimulations; g++) {
			if (verboseOutput == true && g == 0) {
				System.out.println("Games\tRTP\tRTP(Base)\tRTP(Free)");
			}

			/* Print progress report. */
			if (verboseOutput == true && g % progressPrintOnIteration == 0) {
				try {
					System.out.print(g);
					System.out.print("\t");
					System.out.print(String.format("  %6.2f", ((double) wonMoney / (double) lostMoney)));
					System.out.print("\t");
					System.out.print(String.format("  %6.2f", ((double) baseMoney / (double) lostMoney)));
					System.out.print("\t");
					System.out.print(String.format("  %6.2f", ((double) freeMoney / (double) lostMoney)));
				} catch (Exception e) {
					System.err.println(e);
				}
				System.out.println();
			}

			totalNumberOfGames++;

			lostMoney += totalBet;

			singleBaseGame();
		}

		System.out.println("********************************************************************************");
		printStatistics();
		System.out.println("********************************************************************************");
	}
}
