public class ASCIIRotationVariants {
    
    private static final char[] DEPTH_CHARS_CLASSIC = {'@', '#', '*', '+', '=', '-', ':', '.', ' '};
    private static final char[] DEPTH_CHARS_DENSE = {'@', '&', '#', '*', '%', '+', '=', '-', ':', '.', ' '};
    private static final char[] DEPTH_CHARS_SIMPLE = {'@', '*', '+', '.', ' '};
    private static final char[] DEPTH_CHARS_MODERN = {'#', '@', '*', '&', '%', '+', '=', '-', '~', '.', ' '};
    
    public static void main(String[] args) {
        Vector origin = new Vector(0, 0, 0);
        Surface surface = new Surface(origin, "@", 18);
        
        System.out.println("ASCII 3D Rotation Demo - Choose your style:");
        System.out.println("1. Classic ASCII rotation");
        System.out.println("2. Dense character rotation");
        System.out.println("3. Simple rotation");
        System.out.println("4. Wave pattern rotation");
        System.out.println("5. Spiral rotation");
        System.out.println();
        
        classicASCIIRotation(surface);
    }
    
    public static void classicASCIIRotation(Surface surface) {
        System.out.println("Classic ASCII Rotation - Press Ctrl+C to stop");
        double time = 0;
        
        while (true) {
            clearScreen();
            drawASCII3D(surface, DEPTH_CHARS_CLASSIC);
            
            double angleX = Math.sin(time * 0.3) * 0.5;
            double angleY = time * 0.8;
            double angleZ = Math.cos(time * 0.2) * 0.3;
            
            surface.setRotationAngles(angleX, angleY, angleZ);
            
            System.out.printf("Time: %.1fs | X:%.0f° Y:%.0f° Z:%.0f°\n", 
                            time, Math.toDegrees(angleX), 
                            Math.toDegrees(angleY) % 360, Math.toDegrees(angleZ));
            
            time += 0.1;
            
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public static void denseCharacterRotation(Surface surface) {
        System.out.println("Dense Character Rotation - Press Ctrl+C to stop");
        double angle = 0;
        
        while (true) {
            clearScreen();
            drawASCII3D(surface, DEPTH_CHARS_DENSE);
            
            surface.setRotationAngles(angle * 0.7, angle, angle * 0.4);
            
            System.out.println("=== DENSE ASCII ROTATION ===");
            System.out.printf("Rotation: %.0f degrees\n", Math.toDegrees(angle) % 360);
            
            angle += Math.PI / 20;
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public static void wavePatternRotation(Surface surface) {
        System.out.println("Wave Pattern Rotation - Press Ctrl+C to stop");
        double time = 0;
        
        while (true) {
            clearScreen();
            drawWaveASCII(surface, time);
            
            double waveX = Math.sin(time) * Math.PI / 3;
            double waveY = time * 1.2;
            double waveZ = Math.sin(time * 1.5) * Math.PI / 4;
            
            surface.setRotationAngles(waveX, waveY, waveZ);
            
            System.out.println("~~~ WAVE PATTERN ROTATION ~~~");
            System.out.printf("Wave Time: %.2f\n", time);
            
            time += 0.08;
            
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public static void spiralRotation(Surface surface) {
        System.out.println("Spiral Rotation - Press Ctrl+C to stop");
        double spiral = 0;
        
        while (true) {
            clearScreen();
            drawSpiralASCII(surface, spiral);
            
            double radius = Math.abs(Math.sin(spiral * 0.1)) * Math.PI / 2;
            surface.setRotationAngles(radius, spiral * 2, radius * 0.5);
            
            System.out.println("*** SPIRAL ROTATION ***");
            System.out.printf("Spiral: %.1f | Radius: %.2f\n", spiral, radius);
            
            spiral += 0.15;
            
            try {
                Thread.sleep(110);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public static void drawASCII3D(Surface surface, char[] depthChars) {
        int screenWidth = 70;
        int screenHeight = 50;
        char[][] screen = new char[screenHeight][screenWidth];
        
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screen[i][j] = ' ';
            }
        }
        
        int surfaceSize = surface.getWidth();
        double scale = 1.8;
        
        for (int i = 0; i < surfaceSize; i++) {
            for (int j = 0; j < surfaceSize; j++) {
                for (int depth = -4; depth <= 4; depth++) {
                    Vector point = new Vector(
                        (i - surfaceSize/2.0) * scale,
                        (j - surfaceSize/2.0) * scale,
                        depth * 0.3
                    );
                    
                    Vector rotated = surface.rotateVector(point);
                    
                    int screenX = (int)(rotated.i + screenWidth/2);
                    int screenY = (int)(rotated.j + screenHeight/2);
                    
                    if (screenX >= 0 && screenX < screenWidth && 
                        screenY >= 0 && screenY < screenHeight) {
                        
                        int depthIndex = Math.min(Math.abs(depth), depthChars.length - 1);
                        char depthChar = depthChars[depthIndex];
                        
                        if (screen[screenY][screenX] == ' ') {
                            screen[screenY][screenX] = depthChar;
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                System.out.print(screen[i][j]);
            }
            System.out.println();
        }
    }
    
    public static void drawWaveASCII(Surface surface, double time) {
        int screenWidth = 65;
        int screenHeight = 50;
        char[][] screen = new char[screenHeight][screenWidth];
        
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screen[i][j] = ' ';
            }
        }
        
        int surfaceSize = surface.getWidth();
        double scale = 1.5;
        
        for (int i = 0; i < surfaceSize; i++) {
            for (int j = 0; j < surfaceSize; j++) {
                // Add wave effect based on position and time
                double wave = Math.sin((i + j) * 0.5 + time * 3) * 2;
                
                Vector point = new Vector(
                    (i - surfaceSize/2.0) * scale,
                    (j - surfaceSize/2.0) * scale,
                    wave
                );
                
                Vector rotated = surface.rotateVector(point);
                
                int screenX = (int)(rotated.i + screenWidth/2);
                int screenY = (int)(rotated.j + screenHeight/2);
                
                if (screenX >= 0 && screenX < screenWidth && 
                    screenY >= 0 && screenY < screenHeight) {
                    
                    // Choose character based on wave height
                    char waveChar;
                    if (wave > 1.5) waveChar = '@';
                    else if (wave > 0.5) waveChar = '#';
                    else if (wave > -0.5) waveChar = '*';
                    else if (wave > -1.5) waveChar = '+';
                    else waveChar = '.';
                    
                    screen[screenY][screenX] = waveChar;
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
    
    public static void drawSpiralASCII(Surface surface, double spiral) {
        int screenWidth = 60;
        int screenHeight = 50;
        char[][] screen = new char[screenHeight][screenWidth];
        char[] spiralChars = {'@', '#', '&', '*', '%', '+', '=', '-', ':', '.', ' '};
        
        // Initialize screen
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screen[i][j] = ' ';
            }
        }
        
        int surfaceSize = surface.getWidth();
        double scale = 1.6;
        
        for (int i = 0; i < surfaceSize; i++) {
            for (int j = 0; j < surfaceSize; j++) {
                // Create spiral effect
                double distance = Math.sqrt((i - surfaceSize/2.0) * (i - surfaceSize/2.0) + 
                                          (j - surfaceSize/2.0) * (j - surfaceSize/2.0));
                double spiralZ = Math.sin(distance * 0.8 + spiral) * 1.5;
                
                Vector point = new Vector(
                    (i - surfaceSize/2.0) * scale,
                    (j - surfaceSize/2.0) * scale,
                    spiralZ
                );
                
                Vector rotated = surface.rotateVector(point);
                
                int screenX = (int)(rotated.i + screenWidth/2);
                int screenY = (int)(rotated.j + screenHeight/2);
                
                if (screenX >= 0 && screenX < screenWidth && 
                    screenY >= 0 && screenY < screenHeight) {
                    
                    int charIndex = (int)(distance + spiral * 2) % spiralChars.length;
                    screen[screenY][screenX] = spiralChars[Math.abs(charIndex)];
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
        // ANSI escape codes to clear screen
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }
}