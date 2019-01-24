package entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Train extends Thread {

        private int number;
        private boolean direction;

        private static final int TIME_IN_TUNNEL_IN_SECONDS = 3;
        private static final Logger LOGGER = LogManager.getLogger(Train.class);

        public Train(){}

        public int getNumber(){
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public boolean getDirection() {
            return direction;
        }

        public void setDirection(boolean direction) {
            this.direction = direction;
        }
        @Override
        public void run(){
        }

        @Override
        public String toString() {
            return getClass().getSimpleName()
                    + " №" + number
                    + ", " + direction + " direction";
        }
}
