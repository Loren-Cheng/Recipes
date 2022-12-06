package recipes.exception;

public class ExceptionInRecipe extends RuntimeException {
    public static class RecipeNotFoundException extends RuntimeException {
        private static final String message = "Not found";

        public RecipeNotFoundException() {
            super(message);
        }
    }

    public static class RecipeNotValidatedException extends RuntimeException {
        private static final String message = "Not found";

        public RecipeNotValidatedException() {
            super();
        }
    }

}
