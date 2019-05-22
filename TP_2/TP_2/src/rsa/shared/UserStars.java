
/**
 * 
 * Classification to a ride provided by the other user.
 * 
 */

package rsa.shared;

public enum UserStars {
	// Great ride
	FIVE_STARS(5),
	// Good ride
	FOUR_STARS(4),
	// Lousy ride
	ONE_STAR(1),
	// Average ride
	THREE_STARS(3),
	// Bad ride
	TWO_STARS(2);
	
	int stars;
	
	private UserStars(int stars) {
        this.stars = stars;
    }
	public int	getStars() {
		return stars;
	}
}
