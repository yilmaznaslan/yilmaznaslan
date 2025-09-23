# Microsoft Certified: Azure AI Engineer Associate

## Content

- Plan and manage an Azure AI solution (15–20%)
- Implement content moderation solutions (10–15%)
- Implement computer vision solutions (15–20%)
- Implement natural language processing solutions (30–35%)
- Implement knowledge mining and document intelligence solutions (10–15%)
  - Implement an Azure AI Search solution
  - Implement an Azure AI Document Intelligence solution
- Implement generative AI solutions (10–15%)

### Plan and manage an Azure AI solution (15–20%)

- Select the appropriate Azure AI service
  - [ ] Select the appropriate service for a computer vision solution
  - [ ] Select the appropriate service for a natural language processing solution
  - [ ] Select the appropriate service for a speech solution
  - [ ] Select the appropriate service for a generative AI solution
  - [ ] Select the appropriate service for a document intelligence solution
  - [ ] Select the appropriate service for a knowledge mining solution
- Plan, create and deploy an Azure AI service

  - [ ] Plan for a solution that meets Responsible AI principles
  - [ ] Create an Azure AI resource
  - [ ] Determine a default endpoint for a service
  - [ ] Integrate Azure AI services into a continuous integration and continuous delivery (CI/CD) pipeline
  - [ ] Plan and implement a container deployment

- Manage, monitor, and secure an Azure AI service
  - [ ] Configure diagnostic logging
  - [ ] Monitor an Azure AI resource
  - [ ] Manage costs for Azure AI services
  - [ ] Manage account keys
  - [ ] Protect account keys by using Azure Key Vault
  - [ ] Manage authentication for an Azure AI Service resource
  - [ ] Manage private communications

#### Manage, monitor, and secure an Azure AI service

##### Configuring diagnostic logging

- Azure AI services provide diagnostic logging that can be used to monitor the health and performance of the service.
- The following resources needs to be created for diagnostic log storage before configuring the diagnostic logging for
  your AI services resource
  - **Azure Storage Account** - A cloud-based data store that you can use to store log archives. It should be in the same
    region of the AI service resource.
  - **Azure Log Analytics** - A service that enables you to query and visualize log data within the Azure Portal

##### AZ CLI commands

- `az group list` : List all resource groups in a subscription
- `az resource list`: List all resources in a resource group
- `az <service-group> <resource-type> <action> [required-parameters] [optional-parameters]`: Perform an action on a
  resource
  - Example: `az cognitiveservices account list --resource-group ai-102-study-resouruce-group`: List all Cognitive
    Services accounts in a resource group
  - Example: `az cognitiveservices account show --name ai-102-azure-language-service --resource-group ai-102-study-resouruce-group`:
    S Show the details of a Cognitive Services account

##### Manage account keys

You can develop applications that consume Azure AI services by using a key for authentication. However,
this means that the application code must be able to obtain the key. One option is to store the key in an
environment variable or a configuration file where the application is deployed, but this approach leaves the key
vulnerable to unauthorized access.
A better approach when developing applications on Azure is to store the key securely in Azure Key Vault,
and provide access to the key through a **managed identity** (in other words, a user account used by the application
itself).

- Access to Key Vault is granted to **security principles** which are user identities that are authenticated using
  Microsoft Entra ID.
- Admins assign **Security principles** to applications to define a managed identity for the application.
- The application uses this managed identity to access the **key vault**.

##### Manage authentication for an Azure AI Service resource

Azure AI services supports Microsoft Entra ID authentication, enabling you to access to specific service principles or
managed identities for apps and services running in Azure
There different way to authenticate against Azure AI services using Microsoft Entra ID

1. Authenticate using a service principle
2. Authenticate using a managed identity

- If the application is running in Azure, you can use a **managed identity** to authenticate against Azure AI services. Less
  administrative overhead than using a service principle.
- If the application is running outside Azure, you can use a **service principle** to authenticate against Azure AI
  services.

###### Using a managed identity

Managed identities come in two types;

- _System-assigned managed identity_: A managed identity is created and linked to a specific resource
- _User-assigned managed identity_: A managed identity is created to be usable by multiple resources

Steps;

1. Enable managed identity for the application
   - If your backend application is hosted on an Azure service like an Azure VM, App Service, or Azure Functions, you
     can enable a System-Assigned Managed Identity.
2. Grant the managed identity access to the Azure AI service using **role-based access control (RBAC)** permission
   - Assign the appropriate role to the Managed Identity to allow it to access the Azure AI service. For instance, if
     you are accessing Azure Cognitive Services, you might assign the Cognitive Services User role.

##### Network access

If you enable the firewall for the Azure AI Services account, you need to allow network access to the service. You can achieve this by either allowing access from a specific virtual network or adding an IP range to the firewall rules.

### Implement content moderation solutions (10–15%)

Create solutions for content delivery

- Implement a text moderation solution with Azure AI Content Safety
- Implement an image moderation solution with Azure AI Content Safety

#### Features

- Prompt shield: Scans text for the risk of a User input attack on a Large Language Model
- Groundedness detection: Detects if a text is grounded in reality
- Protected material text detection
- Analyze Text API
- Analyze Image API

### Implement computer vision solutions (15–20%)

#### Analyze images using Azure AI Vision

- [ ] Select visual features to meet image processing requirements
- [ ] Detect objects in images and generate image tags
- [ ] Include image analysis features in an image processing request
- [ ] Interpret image processing responses
- [ ] Extract text from images using Azure AI Vision
- [ ] Convert handwritten text using Azure AI Vision

##### Features

- Description and tag generation - determining an appropriate caption for an image, and identifying relevant "tags" that
  can be used as keywords to indicate its subject.
- Object detection - detecting the presence and location of specific objects within the image.
- People detection - detecting the presence, location, and features of people in the image.
- Image metadata, color, and type analysis - determining the format and size of an image, its dominant color palette,
  and whether it contains clip art.
- Category identification - identifying an appropriate categorization for the image, and if it contains any known
  landmarks.
- Background removal - detecting the background in an image and output the image with the background transparent or a
  greyscale alpha matte image.
- Moderation rating - determine if the image includes any adult or violent content.
- Optical character recognition - reading text in the image.
- Smart thumbnail generation - identifying the main region of interest in the image to create a smaller "thumbnail"
  version.

- VisualFeatures.TAGS: Identifies tags about the image, including objects, scenery, setting, and actions
- VisualFeatures.OBJECTS: Returns the bounding box for each detected object
- VisualFeatures.CAPTION: Generates a caption of the image in natural language
- VisualFeatures.DENSE_CAPTIONS: Generates more detailed captions for the objects detected in addition to the describing
  the whole image
  - Use gender-neutral-caption parameter to true to generate
- VisualFeatures.PEOPLE: Returns the bounding box for detected people
- VisualFeatures.SMART_CROPS: Returns the bounding box of the specified aspect ratio for the area of interest
- VisualFeatures.READ: Extracts readable text

##### Reading Text from Images

1. Image Analysis Optical character recognition (OCR):
   - Use this feature for general, handwritten or unstructured documents with smaller amount of text, or images that
     contain text. High accuracy. Large number of files
   - Results are returned immediately (synchronous) from a single API call.
   - Best suitable for short sections of hand written text or text in images.
2. Document Intelligence:
   - Use this service to read small to large volumes of text from images and **PDF** documents.
   - This service uses context and structure of the document to improve accuracy.
   - The initial function call returns an asynchronous operation ID, which must be used in a subsequent call to
     retrieve the results.
   - Examples include: receipts, articles, and invoices.

###### OCR

- The resulting JSON contains divisions for block, line, words and bounding box for each word and line.
- **Supports multiple languages**
- High accuracy

#### Analyze Videos

- [ ] Use Azure AI Video Indexer to extract insights from a video or live stream
- [ ] Use Azure AI Vision **Spatial Analysis** to **detect presence and movement of people** in video

##### Azure AI Video Indexer

##### Customizing the language model

- Teach Video Indexer to recognize specific vocabulary or industry terms.
- Give enough real examples of sentences as they would be spoken.
- Put only one sentence per line, not more. Otherwise the system will learn probabilities across sentences.
- It's okay to put one word as a sentence to boost the word against others, but the system learns best from full
  sentences.
- When introducing new words or acronyms, if possible, give as many examples of usage in a full sentence to give as much
  context as possible to the system.
- Try to put several adaptation options, and see how they work for you.
- Avoid repetition of the exact same sentence multiple times. It may create bias against the rest of the input.
- Avoid including uncommon symbols (~, # @ % &) as they'll get discarded. The sentences in which they appear will also
  get discarded.
- Avoid putting too large inputs, such as hundreds of thousands of sentences, because doing so will dilute the effect of
  boosting.

###### Customize language models by correcting transcript

#### Implement custom computer vision models by using Azure AI Vision

Custom models in Azure AI Vision allow you to train an AI model to **classify images** or **detect objects** in images.

- [ ] Choose between image classification and object detection and product recognition
- [ ] Label images
- [ ] Train a custom image model, including image classification and object detection
- [ ] Evaluate custom vision model metrics
- [ ] Publish a custom vision model
- [ ] Consume a custom vision model

Notes;

- COCO file that defines the label information about those images.
- At least 3-5 images per class to train custom image classification
- For the custom image classification model, there are 2 classification types
  - Multilabel classification: Assigns multiple labels to an image
  - Multiclass classification: Assigns a single label to an image
- A custom image classification project is done via ai custom vision. The trained model can be published to
  publish a trained Azure AI Custom Vision model to either an Azure AI Custom Vision (Prediction)
  - or Azure AI Services resource
- Evaluating the classifier

### Implement natural language processing solutions

Extracts meanings from natural language. The features of the Language service can be split into two categories

- Preconfigured features;
  - Summarization
  - Named Entity Recognition
  - PII detection
  - Key Phrase Detection
  - Language detection
- Learned features
  - Conversational language understanding (CLU)
  - Custom named entity recognition
  - Custom text classification
  - Question answering

1. Analyze text by using Azure AI Language

   - Extract key phrases
   - Extract entities
   - Determine sentiment of text
   - Detect the language used in text
   - Detect personally identifiable information (PII) in text

2. Process speech by using Azure AI Speech

   - Implement text-to-speech
   - Implement speech-to-text
   - Improve text-to-speech by using Speech Synthesis Markup Language (SSML)
   - Implement custom speech solutions
   - Implement intent recognition
   - Implement keyword recognition

3. Translate language
   - Translate text and documents by using the **Azure AI Translator** service
   - Implement custom translation, including training, improving, and publishing a custom model
   - Translate speech-to-speech by using the Azure AI Speech service
   - Translate speech-to-text by using the Azure AI Speech service
   - Translate to multiple languages simultaneously
   -
4. Implement and manage a language understanding model by using Azure AI Language

   - Create intents and add utterances
   - Create entities
   - Train, evaluate, deploy, and test a language understanding model
   - Optimize a language understanding model
   - Consume a language model from a client application
   - Backup and recover language understanding models

5. Create a custom question answering solution by using Azure AI Language
   - Create a custom question answering project
   - Add question-and-answer pairs manually
   - Import sources
   - Train and test a knowledge base
   - Publish a knowledge base
   - Create a multi-turn conversation
   - Add alternate phrasing
   - Add chit-chat to a knowledge base
   - Export a knowledge base
   - Create a multi-language question answering solution

#### Conversational Language Understanding

- Entities;
  - List, prebuilt custom?

#### Custom model

- Standard and advance training modes

#### Process speech by using Azure AI Speech

- Speech-to-text
  - Either in real-time or async with batch transcription
- Text-to-speech
- Realtime Multilanguage Speech To Speech or Speech-To-Text translation
- Speaker recognition
- Intent Recognition

##### Speech To Text API

The speech to text service offers the following core features:

- Real-time transcription: Instant transcription with intermediate results for live audio inputs.
- Fast transcription: Fastest synchronous output for situations with predictable latency.
- Batch transcription: Efficient processing for large volumes of prerecorded audio. Azure Storage is the only way to store
- Custom speech: Models with enhanced accuracy for specific domains and conditions.

###### Custom Speech

- 30 min 50 hours of labelled audio is needed for training
- WER results; 5-10 % considered good
  - Deletion: Because of weak signals
  - Insertion: Because of noisy environment where crosstalk
  - Substitution: Insufficient domain-specific terms

##### Text-to-speech API

Offers

- The Text to speech API, which is the primary way to perform speech synthesis.
- The Batch synthesis API, which is designed to support batch operations that convert large volumes of text to audio -
  for example to generate an audio-book from the source text.
- SSML XMl based markup is used to fine tune speech output pitch, speaking rate volume, pronunciation
- Custom Neural Voice builds highly natural-sounding voices by providing human speech samples as training data
- Use pre built neural voices or custom neural voices

##### Intent Recognition

- Pattern matching: Offline solution for a strict way. It is more aggressive than CLU
  - Exact Phrases; Strings of the exact words that you want to match
  - Patterns: A pattern is a phrase that contains a marked entity.
    - ListEntity: Fuzzy or Strict mode
- Conversational Language Understanding (CLU)
  - Conversational language understanding (CLU) enables users to build custom natural language understanding models to
    predict the overall intention of an incoming utterance and extract important information from it.
  - Both a Speech resource and Language resource are required to use CLU with the Speech SDK. The Speech resource is used to transcribe the user's speech into text, and the Language resource is used to recognize the intent of the utterance

##### Keyword Recognition

- Voice activation of virtual assistant
- The current system is designed to detect a keyword or phrase preceded by a short amount of silence. Detecting a keyword in the middle of a sentence or utterance isn't supported.

##### WER

- Test a baseline or custom model for Word Error Rate( WER) using accuracy test or custom acoustic model
- 5-10%: ready to use
- 20%: acceptable
- 30% needs additional testing

##### Translate Speech

Transcribing spoken input in a specified language, and returning translations of the transcription in **one** or **more
** other languages.

- Audio Config ( Microphone or file )
- SpeechTranslationConfig ( Resource Location,key, Recognation Language, target language),
- TranslationRecognizer.RecognizeOnceAsync

#### Conversational Language Understanding Model

- With conversational language understanding, you can train a model in one language and use to predict intents and entities from utterances in another language you should enable the multi-lingual option for your project while creating or later in project settings. If you notice your model performing poorly in certain languages during the evaluation process, consider adding more data in these languages to your training set.

#### Create Question Answering Solution

- Multi-turn cane be defined either at importing phase or you can define follow up prompts and responses for existing question and answer pairs.
- Improving;
  - Use active learning and synonyms

#### Orchestration workflow

- Does nto support multi languages.
- Orchestration workflow projects do not support the multilingual option, so you need to create a separate workflow project for each language.

#### Translation Service

Features;

- Language detection.
- One-to-many translation.
- Script transliteration (converting text from its native script to an alternative script).

- If the language of the content in the source document is known, it is recommended to specify the source language in the request to get a better translation.
- Apart from translation, the following features are part of Translator: Transliterate, Detect, Dictionary lookup, and Dictionary example.

##### Custom Translation

- BLEU score can be 0-100. between 40 and 60 indicates a high-quality translation.
-

### Implement knowledge mining and document intelligence solutions

#### Implement an Azure AI Search solution

- [ ] Provision an Azure AI Search resource
- [ ] Create data sources
- [ ] Create an index
- [ ] Define a skillset
- [ ] Implement custom skills and include them in a skillset
- [ ] Create and run an indexer
- [ ] Query an index, including syntax, sorting, filtering, and wildcards
- [ ] Manage Knowledge Store projections, including file, object, and table projections

With Azure AI Search, you can:

- Index documents and data from a range of sources.
- Use cognitive skills to enrich index data.
- Store extracted insights in a knowledge store for analysis and integration.

##### Skillset

Skillset defines an enrichment pipeline in which each step enhances the source data with insights obtained by a specific
AI Skill.
The search resources are

##### Custom Azure AI resource skills

- KeyPhraseExtractionSkill
- entityLinkingSkill
- PIIDetectionSkill
- SentimentSkill
- TranslationSkill
- ImageAnalysisSkill

##### Knowledgestore

- **Projections** that determine whether the knowledge store consists of tables, objects or files.
- The type of projection you specify in this structure determines the type of storage used by knowledge store, but not
  its structure. **Fields in tables, objects, and files are determined by Shaper skill output if you're creating the
  knowledge store programmatically**, or by the Import data wizard if you're using the portal.

##### Indexer

##### Index

The index is the searchable result of the indexing process. It consists of a **collection of JSON documents**, with
fields that contain the values extracted during indexing. Client applications can query the index to retrieve, filter,
and sort information.
Each index field can be configured with the following attributes:

- key: Fields that define a unique key for index records.
- searchable: Fields that can be queried using full-text search.
- filterable: Fields that can be included in filter expressions to return only documents that match specified
  constraints.
- sortable: Fields that can be used to order the results.
- facetable: Fields that can be used to determine values for facets (user interface elements used to filter the results
  based on a list of known field values).
- retrievable: Fields that can be included in search results (by default, all fields are retrievable unless this
  attribute is explicitly removed).

- Cosmos DB
- Container
- Azure SQL DB

- Document cracking, field mapping, skillset execution, and output field mapping are the stages of **indexing**.
- Creating a data source, creating an index, and creating and running the indexer are the stages to **create an indexer
  **.
- Connecting to an Azure data source, creating an index schema, and running the wizard to create objects and load data
  are
  the stages for the **Import Data wizard**.

Indexing process;

1. Document cracking -> opening files and extracting content

#### Implement an Azure AI Document Intelligence solution

- [ ] Provision a Document Intelligence resource
- [ ] Use prebuilt models to extract data from documents
- [ ] Implement a custom document intelligence model
- [ ] Train, test, and publish a custom document intelligence model
- [ ] Create a composed document intelligence model

Implement a document intelligence model as a custom Azure AI Search skill

##### Prebuilt models

- JPEG, PNG PDF formats and OFFICE are supported

1. General Document Analysis models

- Read: Extracts words and lines. Supported Files: PDF, JPEG & PNG .. , OfficeWord OfficePPt
- General document: It extends the functionality of the read model by adding the detection of **key-value pairs**, **entities**,
  **selection marks, and tables**.
- Layout: As well as extracting text, the layout model returns **selection marks** and **tables** from the input image or PDF
  file. It's a good model to use when you need rich information about the structure of a document.

2. Specific document type models

- invoice
- receipt
- W2
- ID document
- Business card

4. Composed models

- When you don't know the submitted document type
- The docType property includes the model ID of the custom model that was used to analyze the document.

##### Custom models

- At least 5 examples to train the model
- If there are inaccurencies in the extracted values, retrain with larger dataset

### Implement generative AI solutions (10–15%)

- Use Azure OpenAI Service to generate content

  - Provision an Azure OpenAI Service resource
  - Select and deploy an Azure OpenAI model
  - Submit prompts to generate natural language
  - Submit prompts to generate code
  - Use the DALL-E model to generate images
  - Use Azure OpenAI APIs to submit prompts and receive responses

- Optimize generative AI
  - Configure parameters to control generative behavior
  - Apply prompt engineering techniques to improve responses
  - Use your own data with an Azure OpenAI model
    - pdf, html, txt , md, word, ppt NOT XML AND ZIP
  - Fine-tune an Azure OpenAI model

#### Notes

- Its verison is upgradred to default once its retired. Or you can use Auto-update to default

### Azure OpenAI

- Currently only **TXT**, **MD**, **HTML**, **PDF**, **Microsoft Word**, and **PowerPoint** files can be used and are supported using the “Using
  your data” feature in Azure OpenAI. ZIP and XML files are not supported.

### Dump

- Azure AI Language Service
  - Extract information
    - Extract PII
    - Extract Key Phrases
    - Find linked entities
    - Identify named entities
    - Analyze health related data
    - Custom text extraction
  - Classify Text
    - Analyse sentiment and opinions
    - Custom sentiment analysis
    - Detect language
    - Custom classification
  - Understand Questions And Conversational Language
    - Answer Questions
      - Answer questions (using prebuild API)
      - Custom question answering
    - Conversational language understanding
    - Orchestration workflows
  - Summarize Text
  - Translate Text
- Azure AI Speech Service ( Speech Studio )

  - Speech to text
  - Text to Speech
  - Translate Speech

- Supported file teypes for qeuston answeing data source
