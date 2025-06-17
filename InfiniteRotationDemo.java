public class InfiniteRotationDemo {
    
    public static void main(String[] args) {
        Vector origin = new Vector(0, 0, 0);
        Surface surface = new Surface(origin, "█", 20);
        
        System.out.println("Starting infinite 3D rotation animation...");
        System.out.println("Press Ctrl+C to stop");
        System.out.println();
        
        // Start infinite rotation
        infiniteRotation(surface);
    }
    
    public static void infiniteRotation(Surface surface) {
        double angleStep = Math.PI / 30; // Small incremental steps for smooth animation
        double currentAngle = 0;
        
        while (true) {
            // Clear screen (works on most terminals)
            clearScreen();
            
            // Update rotation angles - rotate around multiple axes for more interesting effect
            double angleX = currentAngle * 0.7;  // Different speeds for each axis
            double angleY = currentAngle * 1.0;
            double angleZ = currentAngle * 0.5;
            
            surface.setRotationAngles(angleX, angleY, angleZ);
            
            // Draw the rotated surface with 3D projection
            draw3DProjection(surface);
            
            // Show current angles
            System.out.printf("Rotation: X=%.1f° Y=%.1f° Z=%.1f°\n", 
                            Math.toDegrees(angleX) % 360,
                            Math.toDegrees(angleY) % 360, 
                            Math.toDegrees(angleZ) % 360);
            
            // Increment angle
            currentAngle += angleStep;
            if (currentAngle > 2 * Math.PI) {
                currentAngle = 0; // Reset to avoid overflow
            }
            
            // Delay for animation effect
            try {
                Thread.sleep(100); // 100ms delay = ~10 FPS
            } catch (InterruptedException e) {
                System.out.println("Animation interrupted!");
                break;
            }
        }
    }
    
    public static void draw3DProjection(Surface surface) {
        int screenWidth = 60;
        int screenHeight = 30;
        char[][] screen = new char[screenHeight][screenWidth];
        
        // Initialize screen with spaces
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screen[i][j] = ' ';
            }
        }
        
        // Create 3D points for the surface
        int surfaceSize = surface.getWidth();
        double scale = 2.0; // Scale factor for visibility
        
        for (int i = 0; i < surfaceSize; i++) {
            for (int j = 0; j < surfaceSize; j++) {
                // Create a 3D point
                Vector point = new Vector(
                    (i - surfaceSize/2.0) * scale,
                    (j - surfaceSize/2.0) * scale,
                    0
                );
                
                // Apply rotation
                Vector rotated = surface.rotateVector(point);
                
                // Project to 2D screen coordinates (simple orthographic projection)
                int screenX = (int)(rotated.i + screenWidth/2);
                int screenY = (int)(rotated.j + screenHeight/2);
                
                // Check bounds and plot
                if (screenX >= 0 && screenX < screenWidth && 
                    screenY >= 0 && screenY < screenHeight) {
                    screen[screenY][screenX] = '█';
                }
                
                // Add some 3D depth by drawing additional points
                for (int depth = -2; depth <= 2; depth++) {
                    Vector depthPoint = new Vector(
                        (i - surfaceSize/2.0) * scale,
                        (j - surfaceSize/2.0) * scale,
                        depth * 0.5
                    );
                    
                    Vector rotatedDepth = surface.rotateVector(depthPoint);
                    
                    int depthScreenX = (int)(rotatedDepth.i + screenWidth/2);
                    int depthScreenY = (int)(rotatedDepth.j + screenHeight/2);
                    
                    if (depthScreenX >= 0 && depthScreenX < screenWidth && 
                        depthScreenY >= 0 && depthScreenY < screenHeight) {
                        
                        // Use different characters for different depths
                        char depthChar = depth == 0 ? '█' : (Math.abs(depth) == 1 ? '▓' : '░');
                        if (screen[depthScreenY][depthScreenX] == ' ') {
                            screen[depthScreenY][depthScreenX] = depthChar;
                        }
                    }
                }
            }
        }
        
        // Draw the screen
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                System.out.print(screen[i][j]);
            }
            System.out.println();
        }
    }
    
    public static void clearScreen() {
        // ANSI escape codes to clear screen and move cursor to top
        System.out.print("\033[2J\033[H");
        System.out.flush();
        
        // Alternative method for systems that don't support ANSI
        // Print multiple newlines to simulate clearing
        // for (int i = 0; i < 50; i++) {
        //     System.out.println();
        // }
    }
    
    // Alternative infinite rotation with different patterns
    public static void infiniteRotationWithPattern(Surface surface) {
        double time = 0;
        
        while (true) {
            clearScreen();
            
            // Create interesting rotation patterns
            double angleX = Math.sin(time * 0.5) * Math.PI / 4;
            double angleY = time;
            double angleZ = Math.cos(time * 0.3) * Math.PI / 6;
            
            surface.setRotationAngles(angleX, angleY, angleZ);
            draw3DProjection(surface);
            
            System.out.printf("Time: %.2f | Pattern Rotation Active\n", time);
            
            time += 0.1;
            
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    // Bouncing rotation animation
    public static void bouncingRotation(Surface surface) {
        double time = 0;
        
        while (true) {
            clearScreen();
            
            // Bouncing effect using sine waves
            double bounce = Math.sin(time * 2) * Math.PI / 3;
            double spin = time * 2;
            
            surface.setRotationAngles(bounce, spin, bounce * 0.5);
            draw3DProjection(surface);
            
            System.out.println("🎯 Bouncing Rotation Mode");
            System.out.printf("Bounce: %.1f° | Spin: %.1f°\n", 
                            Math.toDegrees(bounce), Math.toDegrees(spin) % 360);
            
            time += 0.05;
            
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}