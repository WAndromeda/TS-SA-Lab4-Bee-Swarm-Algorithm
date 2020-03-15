package Help;

//Класс для храненя координат 2-мерной точки в 2-мерной пространстве
public class Interval {
    public Double left, right;

    public Interval(Double left, Double right) {
        if (left >= right)
            throw new RuntimeException("Правая граница должна быть больше левой границы");
        this.left = left;
        this.right = right;
    }

    public Double getLeft() {
            return left;
    }

    public void setLeft(Double left) {
            this.left = left;
    }

    public Double getRight() {
            return right;
    }

    public void setRight(Double right) {
            this.right = right;
    }
}
