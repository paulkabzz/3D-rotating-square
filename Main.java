
public class Main {

public static void main(String[] args) {
    Vector origin = new Vector(0, 0, 0);
    Surface surface = new Surface(origin, "@", 18);
    
    // Choose one:
    ASCIIRotationVariants.classicASCIIRotation(surface);
    // ASCIIRotationVariants.denseCharacterRotation(surface);
    // ASCIIRotationVariants.wavePatternRotation(surface);
    // ASCIIRotationVariants.spiralRotation(surface);
    // InfiniteRotationDemo.infiniteRotation(surface);
    // InfiniteRotationDemo.infiniteRotationWithPattern(surface);
    // InfiniteRotationDemo.bouncingRotation(surface);
}
    
}