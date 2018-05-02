/*
 * This file is part of Goatbot.
 *
 * Goatbot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Goatbot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Goatbot.  If not, see <http://www.gnu.org/licenses/>.
 */
package component.modules.bomb;

public enum WireColors {
    STEELBLUE("SteelBlue"), SNOW("Snow"), PURPLE("Purple"), LIGHTPINK("LightPink"), GOLDENROD("GoldenRod"), BEIGE(
            "Beige"), GREEN("Green"), YELLOW("Yellow"), RED("Red"), BLUE("Blue"), PINK("Pink"), MAGENTA(
            "Magenta"), WHITE("White"), BLACK("Black"), ORANGE("Orange"), AVOCADO("Avocado"), LAVENDER(
            "Lavender"), TEAL("Teal"), BROWN("Brown"), OLIVE("Olive"), MAROON("Maroon"), LIME(
            "Lime"), CYAN("Cyan"), NAVY("Navy"), CORAL("Coral"), GREY("Grey"), MINT(
            "Mint"), POOPBROWN("PoopBrown"), THISWIREWILLKILLYOU("ThisWireWillKillYou");
    private final String color;

    WireColors(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }

    public String getColor() {
        return color;
    }
}
