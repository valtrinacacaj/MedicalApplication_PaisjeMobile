# MedicalApplication_PaisjeMobile

# Përshkrimi i Projektit
Ky është një aplikacion Android që ndihmon përdoruesit të menaxhojnë takimet me mjekët dhe medikamentet e tyre. Aplikacioni përmban mundësinë për regjistrimin e përdoruesve, ruajtjen e të dhënave të përdoruesve (emri, mbiemri, mosha, email-i dhe fjalëkalimi), si dhe menaxhimin e takimeve dhe ilaçeve. Përdoruesit mund të shtojnë, modifikojnë dhe fshijnë takime dhe ilaçe, dhe aplikacioni do t'u dërgojë njoftime në ditët kur kanë takime të planifikuara.
Ky projekt përdor SQLite për menaxhimin e të dhënave lokale dhe përdor bibliotekën BCrypt për kriptimin e fjalëkalimeve.

![image](https://github.com/user-attachments/assets/ae47679b-21bb-41bf-877a-2f7fcb4deb4e)

# Funksionalitete Kryesore
- Regjistrimi dhe identifikimi i përdoruesve: Përdoruesit mund të regjistrohen dhe të identifikohen nëpërmjet fjalëkalimit dhe email-it.
- Menaxhimi i takimeve: Përdoruesit mund të shtojnë, shikojnë dhe fshijnë takime të planifikuara me mjekun.
- Menaxhimi i ilaçeve: Përdoruesit mund të regjistrojnë ilaçet e tyre, të caktuar dozimin dhe orarin.
- Njoftime për takime dhe ilaçe: Aplikacioni dërgon njoftime kur ka takime të planifikuara ose kujtesa për marrjen e ilaçeve.
- Siguria: Fjalëkalimet janë të kriptuara për mbrojtje të të dhënave të përdoruesve.
  
  ![image](https://github.com/user-attachments/assets/19dc1b30-709d-4e44-a8d4-1d658557032f)

  ![image](https://github.com/user-attachments/assets/960c31ab-3e19-4a04-8dda-7e552c8ccb1b)



# Teknologjitë dhe Mjetet e Përdorura
- Android Studio për zhvillimin e aplikacionit.
- SQLite për ruajtjen dhe menaxhimin e të dhënave lokale.
- BCrypt për kriptimin dhe verifikimin e fjalëkalimeve.
- Java për implementimin e logjikës së biznesit.
- SimpleDateFormat për formatimin e datave dhe orareve.
- NotificationManager për menaxhimin e njoftimeve të aplikacionit.

# Instruksione për Ndërtimin dhe Ekzekutimin e Aplikacionit
1. Klononi këtë repository në kompjuterin tuaj:
git clone https://github.com/username/MedicalApplication.git

2. Hapni projektin në Android Studio:
   Hapni Android Studio dhe zgjidhni Open an existing Android Studio project.
   Zgjidhni dosjen që keni klonuar.
   
3.Ndërtoni dhe ekzekutoni aplikacionin:
   Klikoni Run në Android Studio për të ekzekutuar aplikacionin në një emulator ose pajisje fizike Android.
